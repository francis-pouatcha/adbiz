package org.keycloak.services.managers;

import org.jboss.logging.Logger;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;
import org.jboss.resteasy.spi.HttpRequest;
import org.keycloak.ClientConnection;
import org.keycloak.RSATokenVerifier;
import org.keycloak.VerificationException;
import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.broker.provider.IdentityProvider;
import org.keycloak.events.Details;
import org.keycloak.events.EventBuilder;
import org.keycloak.events.EventType;
import org.keycloak.jose.jws.JWSBuilder;
import org.keycloak.login.LoginFormsProvider;
import org.keycloak.models.ClientModel;
import org.keycloak.models.ClientSessionModel;
import org.keycloak.models.RequiredActionProviderModel;
import org.keycloak.models.UserConsentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RequiredCredentialModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserCredentialValueModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserModel.RequiredAction;
import org.keycloak.models.UserSessionModel;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.keycloak.protocol.LoginProtocol;
import org.keycloak.protocol.RestartLoginCookie;
import org.keycloak.protocol.oidc.TokenManager;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.services.resources.IdentityBrokerService;
import org.keycloak.services.resources.LoginActionsService;
import org.keycloak.services.resources.RealmsResource;
import org.keycloak.services.Urls;
import org.keycloak.services.util.CookieHelper;
import org.keycloak.services.validation.Validation;
import org.keycloak.util.Time;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Stateless object that manages authentication
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class AuthenticationManager {
    protected static Logger logger = Logger.getLogger(AuthenticationManager.class);
    public static final String FORM_USERNAME = "username";
    // used for auth login
    public static final String KEYCLOAK_IDENTITY_COOKIE = "KEYCLOAK_IDENTITY";
    // used solely to determine is user is logged in
    public static final String KEYCLOAK_SESSION_COOKIE = "KEYCLOAK_SESSION";
    public static final String KEYCLOAK_REMEMBER_ME = "KEYCLOAK_REMEMBER_ME";
    public static final String KEYCLOAK_LOGOUT_PROTOCOL = "KEYCLOAK_LOGOUT_PROTOCOL";

    protected BruteForceProtector protector;

    public AuthenticationManager() {
    }

    public AuthenticationManager(BruteForceProtector protector) {
        this.protector = protector;
    }

    public BruteForceProtector getProtector() {
        return protector;
    }

    public static boolean isSessionValid(RealmModel realm, UserSessionModel userSession) {
        if (userSession == null) {
            logger.debug("No user session");
            return false;
        }
        int currentTime = Time.currentTime();
        int max = userSession.getStarted() + realm.getSsoSessionMaxLifespan();
        return userSession != null && userSession.getLastSessionRefresh() + realm.getSsoSessionIdleTimeout() > currentTime && max > currentTime;
    }

    public static void expireUserSessionCookie(KeycloakSession session, UserSessionModel userSession, RealmModel realm, UriInfo uriInfo, HttpHeaders headers, ClientConnection connection) {
        try {
            // check to see if any identity cookie is set with the same session and expire it if necessary
            Cookie cookie = headers.getCookies().get(KEYCLOAK_IDENTITY_COOKIE);
            if (cookie == null) return;
            String tokenString = cookie.getValue();
            AccessToken token = RSATokenVerifier.verifyToken(tokenString, realm.getPublicKey(), Urls.realmIssuer(uriInfo.getBaseUri(), realm.getName()), false);
            UserSessionModel cookieSession = session.sessions().getUserSession(realm, token.getSessionState());
            if (cookieSession == null || !cookieSession.getId().equals(userSession.getId())) return;
            expireIdentityCookie(realm, uriInfo, connection);
            expireRememberMeCookie(realm, uriInfo, connection);
        } catch (Exception e) {
        }

    }

    /**
     * Do not logout broker
     *
     * @param session
     * @param realm
     * @param userSession
     * @param uriInfo
     * @param connection
     * @param headers
     */
    public static void backchannelLogout(KeycloakSession session, RealmModel realm,
                                         UserSessionModel userSession, UriInfo uriInfo,
                                         ClientConnection connection, HttpHeaders headers,
                                         boolean logoutBroker) {
        if (userSession == null) return;
        UserModel user = userSession.getUser();
        userSession.setState(UserSessionModel.State.LOGGING_OUT);

        logger.debugv("Logging out: {0} ({1})", user.getUsername(), userSession.getId());
        expireUserSessionCookie(session, userSession, realm, uriInfo, headers, connection);

        for (ClientSessionModel clientSession : userSession.getClientSessions()) {
            backchannelLogoutClientSession(session, realm, clientSession, userSession, uriInfo, headers);
        }
        if (logoutBroker) {
            String brokerId = userSession.getNote(IdentityBrokerService.BROKER_PROVIDER_ID);
            if (brokerId != null) {
                IdentityProvider identityProvider = IdentityBrokerService.getIdentityProvider(session, realm, brokerId);
                try {
                    identityProvider.backchannelLogout(userSession, uriInfo, realm);
                } catch (Exception e) {
                }
            }
        }
        userSession.setState(UserSessionModel.State.LOGGED_OUT);
        session.sessions().removeUserSession(realm, userSession);
    }

    public static void backchannelLogoutClientSession(KeycloakSession session, RealmModel realm, ClientSessionModel clientSession, UserSessionModel userSession, UriInfo uriInfo, HttpHeaders headers) {
        ClientModel client = clientSession.getClient();
        if (client instanceof ClientModel && !client.isFrontchannelLogout() && !ClientSessionModel.Action.LOGGED_OUT.name().equals(clientSession.getAction())) {
            String authMethod = clientSession.getAuthMethod();
            if (authMethod == null) return; // must be a keycloak service like account
            LoginProtocol protocol = session.getProvider(LoginProtocol.class, authMethod);
            protocol.setRealm(realm)
                    .setHttpHeaders(headers)
                    .setUriInfo(uriInfo);
            protocol.backchannelLogout(userSession, clientSession);
            clientSession.setAction(ClientSessionModel.Action.LOGGED_OUT.name());
        }

    }

    // Logout all clientSessions of this user and client
    public static void backchannelUserFromClient(KeycloakSession session, RealmModel realm, UserModel user, ClientModel client, UriInfo uriInfo, HttpHeaders headers) {
        String clientId = client.getId();

        List<UserSessionModel> userSessions = session.sessions().getUserSessions(realm, user);
        for (UserSessionModel userSession : userSessions) {
            List<ClientSessionModel> clientSessions = userSession.getClientSessions();
            for (ClientSessionModel clientSession : clientSessions) {
                if (clientSession.getClient().getId().equals(clientId)) {
                    AuthenticationManager.backchannelLogoutClientSession(session, realm, clientSession, userSession, uriInfo, headers);
                    TokenManager.dettachClientSession(session.sessions(), realm, clientSession);
                }
            }
        }
    }

    public static Response browserLogout(KeycloakSession session, RealmModel realm, UserSessionModel userSession, UriInfo uriInfo, ClientConnection connection, HttpHeaders headers) {
        if (userSession == null) return null;
        UserModel user = userSession.getUser();

        logger.debugv("Logging out: {0} ({1})", user.getUsername(), userSession.getId());
        if (userSession.getState() != UserSessionModel.State.LOGGING_OUT) {
            userSession.setState(UserSessionModel.State.LOGGING_OUT);
        }
        List<ClientSessionModel> redirectClients = new LinkedList<ClientSessionModel>();
        for (ClientSessionModel clientSession : userSession.getClientSessions()) {
            ClientModel client = clientSession.getClient();
            if (ClientSessionModel.Action.LOGGED_OUT.name().equals(clientSession.getAction())) continue;
            if (client.isFrontchannelLogout()) {
                String authMethod = clientSession.getAuthMethod();
                if (authMethod == null) continue; // must be a keycloak service like account
                redirectClients.add(clientSession);
                continue;
            }
            if (client instanceof ClientModel && !client.isFrontchannelLogout()) {
                String authMethod = clientSession.getAuthMethod();
                if (authMethod == null) continue; // must be a keycloak service like account
                LoginProtocol protocol = session.getProvider(LoginProtocol.class, authMethod);
                protocol.setRealm(realm)
                        .setHttpHeaders(headers)
                        .setUriInfo(uriInfo);
                try {
                    logger.debugv("backchannel logout to: {0}", client.getClientId());
                    protocol.backchannelLogout(userSession, clientSession);
                    clientSession.setAction(ClientSessionModel.Action.LOGGED_OUT.name());
                } catch (Exception e) {
                    logger.warn("Failed to logout client, continuing", e);
                }
            }
        }

        for (ClientSessionModel nextRedirectClient : redirectClients) {
            String authMethod = nextRedirectClient.getAuthMethod();
            LoginProtocol protocol = session.getProvider(LoginProtocol.class, authMethod);
            protocol.setRealm(realm)
                    .setHttpHeaders(headers)
                    .setUriInfo(uriInfo);
            // setting this to logged out cuz I"m not sure protocols can always verify that the client was logged out or not
            nextRedirectClient.setAction(ClientSessionModel.Action.LOGGED_OUT.name());
            try {
                logger.debugv("frontchannel logout to: {0}", nextRedirectClient.getClient().getClientId());
                Response response = protocol.frontchannelLogout(userSession, nextRedirectClient);
                if (response != null) {
                    logger.debug("returning frontchannel logout request to client");
                    return response;
                }
            } catch (Exception e) {
                logger.warn("Failed to logout client, continuing", e);
            }

        }
        String brokerId = userSession.getNote(IdentityBrokerService.BROKER_PROVIDER_ID);
        if (brokerId != null) {
            IdentityProvider identityProvider = IdentityBrokerService.getIdentityProvider(session, realm, brokerId);
            Response response = identityProvider.keycloakInitiatedBrowserLogout(userSession, uriInfo, realm);
            if (response != null) return response;
        }
        return finishBrowserLogout(session, realm, userSession, uriInfo, connection, headers);
    }

    public static Response finishBrowserLogout(KeycloakSession session, RealmModel realm, UserSessionModel userSession, UriInfo uriInfo, ClientConnection connection, HttpHeaders headers) {
        expireIdentityCookie(realm, uriInfo, connection);
        expireRememberMeCookie(realm, uriInfo, connection);
        userSession.setState(UserSessionModel.State.LOGGED_OUT);
        String method = userSession.getNote(KEYCLOAK_LOGOUT_PROTOCOL);
        EventBuilder event = new EventBuilder(realm, session, connection);
        LoginProtocol protocol = session.getProvider(LoginProtocol.class, method);
        protocol.setRealm(realm)
                .setHttpHeaders(headers)
                .setUriInfo(uriInfo)
                .setEventBuilder(event);
        Response response = protocol.finishLogout(userSession);
        session.sessions().removeUserSession(realm, userSession);
        return response;
    }


    public static AccessToken createIdentityToken(RealmModel realm, UserModel user, UserSessionModel session, String issuer) {
        AccessToken token = new AccessToken();
        token.id(KeycloakModelUtils.generateId());
        token.issuedNow();
        token.subject(user.getId());
        token.issuer(issuer);
        if (session != null) {
            token.setSessionState(session.getId());
        }
        if (realm.getSsoSessionMaxLifespan() > 0) {
            token.expiration(Time.currentTime() + realm.getSsoSessionMaxLifespan());
        }
        return token;
    }

    public static void createLoginCookie(RealmModel realm, UserModel user, UserSessionModel session, UriInfo uriInfo, ClientConnection connection) {
        String cookiePath = getIdentityCookiePath(realm, uriInfo);
        String issuer = Urls.realmIssuer(uriInfo.getBaseUri(), realm.getName());
        AccessToken identityToken = createIdentityToken(realm, user, session, issuer);
        String encoded = encodeToken(realm, identityToken);
        boolean secureOnly = realm.getSslRequired().isRequired(connection);
        int maxAge = NewCookie.DEFAULT_MAX_AGE;
        if (session.isRememberMe()) {
            maxAge = realm.getSsoSessionMaxLifespan();
        }
        logger.debugv("Create login cookie - name: {0}, path: {1}, max-age: {2}", KEYCLOAK_IDENTITY_COOKIE, cookiePath, maxAge);
        CookieHelper.addCookie(KEYCLOAK_IDENTITY_COOKIE, encoded, cookiePath, null, null, maxAge, secureOnly, true);
        //builder.cookie(new NewCookie(cookieName, encoded, cookiePath, null, null, maxAge, secureOnly));// todo httponly , true);

        String sessionCookieValue = realm.getName() + "/" + user.getId();
        if (session != null) {
            sessionCookieValue += "/" + session.getId();
        }
        // THIS SHOULD NOT BE A HTTPONLY COOKIE!  It is used for OpenID Connect Iframe Session support!
        // Max age should be set to the max lifespan of the session as it's used to invalidate old-sessions on re-login
        CookieHelper.addCookie(KEYCLOAK_SESSION_COOKIE, sessionCookieValue, cookiePath, null, null, realm.getSsoSessionMaxLifespan(), secureOnly, false);

    }

    public static void createRememberMeCookie(RealmModel realm, String username, UriInfo uriInfo, ClientConnection connection) {
        String path = getIdentityCookiePath(realm, uriInfo);
        boolean secureOnly = realm.getSslRequired().isRequired(connection);
        // remember me cookie should be persistent (hardcoded to 365 days for now)
        //NewCookie cookie = new NewCookie(KEYCLOAK_REMEMBER_ME, "true", path, null, null, realm.getCentralLoginLifespan(), secureOnly);// todo httponly , true);
        CookieHelper.addCookie(KEYCLOAK_REMEMBER_ME, "username:" + username, path, null, null, 31536000, secureOnly, true);
    }

    public static String getRememberMeUsername(RealmModel realm, HttpHeaders headers) {
        if (realm.isRememberMe()) {
            Cookie cookie = headers.getCookies().get(AuthenticationManager.KEYCLOAK_REMEMBER_ME);
            if (cookie != null) {
                String value = cookie.getValue();
                String[] s = value.split(":");
                if (s[0].equals("username") && s.length == 2) {
                    return s[1];
                }
            }
        }
        return null;
    }

    protected static String encodeToken(RealmModel realm, Object token) {
        String encodedToken = new JWSBuilder()
                .jsonContent(token)
                .rsa256(realm.getPrivateKey());
        return encodedToken;
    }

    public static void expireIdentityCookie(RealmModel realm, UriInfo uriInfo, ClientConnection connection) {
        logger.debug("Expiring identity cookie");
        String path = getIdentityCookiePath(realm, uriInfo);
        expireCookie(realm, KEYCLOAK_IDENTITY_COOKIE, path, true, connection);
        expireCookie(realm, KEYCLOAK_SESSION_COOKIE, path, false, connection);
    }
    public static void expireRememberMeCookie(RealmModel realm, UriInfo uriInfo, ClientConnection connection) {
        logger.debug("Expiring remember me cookie");
        String path = getIdentityCookiePath(realm, uriInfo);
        String cookieName = KEYCLOAK_REMEMBER_ME;
        expireCookie(realm, cookieName, path, true, connection);
    }

    protected static String getIdentityCookiePath(RealmModel realm, UriInfo uriInfo) {
        return getRealmCookiePath(realm, uriInfo);
    }

    public static String getRealmCookiePath(RealmModel realm, UriInfo uriInfo) {
        URI uri = RealmsResource.realmBaseUrl(uriInfo).build(realm.getName());
        return uri.getRawPath();
    }

    public static void expireCookie(RealmModel realm, String cookieName, String path, boolean httpOnly, ClientConnection connection) {
        logger.debugv("Expiring cookie: {0} path: {1}", cookieName, path);
        boolean secureOnly = realm.getSslRequired().isRequired(connection);;
        CookieHelper.addCookie(cookieName, "", path, null, "Expiring cookie", 0, secureOnly, httpOnly);
    }

    public AuthResult authenticateIdentityCookie(KeycloakSession session, RealmModel realm) {
        return authenticateIdentityCookie(session, realm, true);
    }

    public static AuthResult authenticateIdentityCookie(KeycloakSession session, RealmModel realm, boolean checkActive) {
        Cookie cookie = session.getContext().getRequestHeaders().getCookies().get(KEYCLOAK_IDENTITY_COOKIE);
        if (cookie == null || "".equals(cookie.getValue())) {
            logger.debugv("Could not find cookie: {0}", KEYCLOAK_IDENTITY_COOKIE);
            return null;
        }

        String tokenString = cookie.getValue();
        AuthResult authResult = verifyIdentityToken(session, realm, session.getContext().getUri(), session.getContext().getConnection(), checkActive, tokenString, session.getContext().getRequestHeaders());
        if (authResult == null) {
            expireIdentityCookie(realm, session.getContext().getUri(), session.getContext().getConnection());
            return null;
        }
        authResult.getSession().setLastSessionRefresh(Time.currentTime());
        return authResult;
    }


    public static Response redirectAfterSuccessfulFlow(KeycloakSession session, RealmModel realm, UserSessionModel userSession,
                                                ClientSessionModel clientSession,
                                                HttpRequest request, UriInfo uriInfo, ClientConnection clientConnection) {
        Cookie sessionCookie = request.getHttpHeaders().getCookies().get(AuthenticationManager.KEYCLOAK_SESSION_COOKIE);
        if (sessionCookie != null) {

            String[] split = sessionCookie.getValue().split("/");
            if (split.length >= 3) {
                String oldSessionId = split[2];
                if (!oldSessionId.equals(userSession.getId())) {
                    UserSessionModel oldSession = session.sessions().getUserSession(realm, oldSessionId);
                    if (oldSession != null) {
                        logger.debugv("Removing old user session: session: {0}", oldSessionId);
                        session.sessions().removeUserSession(realm, oldSession);
                    }
                }
            }
        }
        // refresh the cookies!
        createLoginCookie(realm, userSession.getUser(), userSession, uriInfo, clientConnection);
        if (userSession.getState() != UserSessionModel.State.LOGGED_IN) userSession.setState(UserSessionModel.State.LOGGED_IN);
        if (userSession.isRememberMe()) createRememberMeCookie(realm, userSession.getUser().getUsername(), uriInfo, clientConnection);
        LoginProtocol protocol = session.getProvider(LoginProtocol.class, clientSession.getAuthMethod());
        protocol.setRealm(realm)
                .setHttpHeaders(request.getHttpHeaders())
                .setUriInfo(uriInfo);
        RestartLoginCookie.expireRestartCookie(realm, clientConnection, uriInfo);
        return protocol.authenticated(userSession, new ClientSessionCode(realm, clientSession));

    }

    public static Response nextActionAfterAuthentication(KeycloakSession session, UserSessionModel userSession, ClientSessionModel clientSession,
                                                  ClientConnection clientConnection,
                                                  HttpRequest request, UriInfo uriInfo, EventBuilder event) {
        Response requiredAction = actionRequired(session, userSession, clientSession, clientConnection, request, uriInfo, event);
        if (requiredAction != null) return requiredAction;
        event.success();
        RealmModel realm = clientSession.getRealm();
        return redirectAfterSuccessfulFlow(session, realm , userSession, clientSession, request, uriInfo, clientConnection);

    }

    public static Response actionRequired(final KeycloakSession session, final UserSessionModel userSession, final ClientSessionModel clientSession,
                                                         final ClientConnection clientConnection,
                                                         final HttpRequest request, final UriInfo uriInfo, final EventBuilder event) {
        final RealmModel realm = clientSession.getRealm();
        final UserModel user = userSession.getUser();
        final ClientModel client = clientSession.getClient();

        RequiredActionContext context = evaluateRequiredActionTriggers(session, userSession, clientSession, clientConnection, request, uriInfo, event, realm, user);


        logger.debugv("processAccessCode: go to oauth page?: {0}", client.isConsentRequired());

        event.detail(Details.CODE_ID, clientSession.getId());

        Set<String> requiredActions = user.getRequiredActions();
        for (String action : requiredActions) {
            RequiredActionProviderModel model = realm.getRequiredActionProviderByAlias(action);
            RequiredActionProvider actionProvider = session.getProvider(RequiredActionProvider.class, model.getProviderId());
            Response challenge = actionProvider.invokeRequiredAction(context);
            if (challenge != null) {
                return challenge;
            }

        }
        if (client.isConsentRequired()) {

            UserConsentModel grantedConsent = user.getConsentByClient(client.getId());

            List<RoleModel> realmRoles = new LinkedList<>();
            MultivaluedMap<String, RoleModel> resourceRoles = new MultivaluedMapImpl<>();
            ClientSessionCode accessCode = new ClientSessionCode(realm, clientSession);
            for (RoleModel r : accessCode.getRequestedRoles()) {

                // Consent already granted by user
                if (grantedConsent != null && grantedConsent.isRoleGranted(r)) {
                    continue;
                }

                if (r.getContainer() instanceof RealmModel) {
                    realmRoles.add(r);
                } else {
                    resourceRoles.add(((ClientModel) r.getContainer()).getClientId(), r);
                }
            }

            List<ProtocolMapperModel> protocolMappers = new LinkedList<>();
            for (ProtocolMapperModel protocolMapper : accessCode.getRequestedProtocolMappers()) {
                if (protocolMapper.isConsentRequired() && protocolMapper.getConsentText() != null) {
                    if (grantedConsent == null || !grantedConsent.isProtocolMapperGranted(protocolMapper)) {
                        protocolMappers.add(protocolMapper);
                    }
                }
            }

            // Skip grant screen if everything was already approved by this user
            if (realmRoles.size() > 0 || resourceRoles.size() > 0 || protocolMappers.size() > 0) {
                accessCode.setAction(ClientSessionModel.Action.OAUTH_GRANT.name());

                return session.getProvider(LoginFormsProvider.class)
                        .setClientSessionCode(accessCode.getCode())
                        .setAccessRequest(realmRoles, resourceRoles, protocolMappers)
                        .createOAuthGrant(clientSession);
            } else {
                String consentDetail = (grantedConsent != null) ? Details.CONSENT_VALUE_PERSISTED_CONSENT : Details.CONSENT_VALUE_NO_CONSENT_REQUIRED;
                event.detail(Details.CONSENT, consentDetail);
            }
        } else {
            event.detail(Details.CONSENT, Details.CONSENT_VALUE_NO_CONSENT_REQUIRED);
        }
        return null;

    }

    public static RequiredActionContext evaluateRequiredActionTriggers(final KeycloakSession session, final UserSessionModel userSession, final ClientSessionModel clientSession, final ClientConnection clientConnection, final HttpRequest request, final UriInfo uriInfo, final EventBuilder event, final RealmModel realm, final UserModel user) {
        RequiredActionContext context = new RequiredActionContext() {
            @Override
            public EventBuilder getEvent() {
                return event;
            }

            @Override
            public UserModel getUser() {
                return user;
            }

            @Override
            public RealmModel getRealm() {
                return realm;
            }

            @Override
            public ClientSessionModel getClientSession() {
                return clientSession;
            }

            @Override
            public UserSessionModel getUserSession() {
                return userSession;
            }

            @Override
            public ClientConnection getConnection() {
                return clientConnection;
            }

            @Override
            public UriInfo getUriInfo() {
                return uriInfo;
            }

            @Override
            public KeycloakSession getSession() {
                return session;
            }

            @Override
            public HttpRequest getHttpRequest() {
                return request;
            }

            @Override
            public String generateAccessCode(String action) {
                ClientSessionCode code = new ClientSessionCode(getRealm(), getClientSession());
                code.setAction(action);
                return code.getCode();
            }
        };

        // see if any required actions need triggering, i.e. an expired password
        for (RequiredActionProviderModel model : realm.getRequiredActionProviders()) {
            if (!model.isEnabled()) continue;
            RequiredActionProvider provider = session.getProvider(RequiredActionProvider.class, model.getProviderId());
            provider.evaluateTriggers(context);
        }
        return context;
    }


    protected static AuthResult verifyIdentityToken(KeycloakSession session, RealmModel realm, UriInfo uriInfo, ClientConnection connection, boolean checkActive, String tokenString, HttpHeaders headers) {
        try {
            AccessToken token = RSATokenVerifier.verifyToken(tokenString, realm.getPublicKey(), Urls.realmIssuer(uriInfo.getBaseUri(), realm.getName()), checkActive);
            if (checkActive) {
                if (!token.isActive() || token.getIssuedAt() < realm.getNotBefore()) {
                    logger.debug("identity cookie expired");
                    return null;
                } else {
                    logger.debugv("token active - active: {0}, issued-at: {1}, not-before: {2}", token.isActive(), token.getIssuedAt(), realm.getNotBefore());
                }
            }

            UserModel user = session.users().getUserById(token.getSubject(), realm);
            if (user == null || !user.isEnabled() ) {
                logger.debug("Unknown user in identity token");
                return null;
            }

            UserSessionModel userSession = session.sessions().getUserSession(realm, token.getSessionState());
            if (!isSessionValid(realm, userSession)) {
                if (userSession != null) backchannelLogout(session, realm, userSession, uriInfo, connection, headers, true);
                logger.debug("User session not active");
                return null;
            }

            return new AuthResult(user, userSession, token);
        } catch (VerificationException e) {
            logger.debug("Failed to verify identity token", e);
        }
        return null;
    }

    public enum AuthenticationStatus {
        SUCCESS, ACCOUNT_TEMPORARILY_DISABLED, ACCOUNT_DISABLED, ACTIONS_REQUIRED, INVALID_USER, INVALID_CREDENTIALS, MISSING_PASSWORD, MISSING_TOTP, FAILED
    }

    public static class AuthResult {
        private final UserModel user;
        private final UserSessionModel session;
        private final AccessToken token;

        public AuthResult(UserModel user, UserSessionModel session, AccessToken token) {
            this.user = user;
            this.session = session;
            this.token = token;
        }

        public UserSessionModel getSession() {
            return session;
        }

        public UserModel getUser() {
            return user;
        }

        public AccessToken getToken() {
            return token;
        }
    }

}
