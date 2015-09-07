package org.keycloak.services.resources;

import org.jboss.logging.Logger;
import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.HttpRequest;
import org.keycloak.AbstractOAuthClient;
import org.keycloak.ClientConnection;
import org.keycloak.OAuth2Constants;
import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.protocol.oidc.OIDCLoginProtocolService;
import org.keycloak.services.ForbiddenException;
import org.keycloak.services.Urls;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.Auth;
import org.keycloak.services.managers.AuthenticationManager;
import org.keycloak.services.util.CookieHelper;
import org.keycloak.util.UriUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Set;
import java.util.UUID;

/**
 * Helper class for securing local services.  Provides login basics as well as CSRF check basics
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public abstract class AbstractSecuredLocalService {
    private static final Logger logger = Logger.getLogger(AbstractSecuredLocalService.class);
    protected final ClientModel client;
    protected RealmModel realm;

    @Context
    protected UriInfo uriInfo;
    @Context
    protected HttpHeaders headers;
    @Context
    protected ClientConnection clientConnection;
    protected String stateChecker;
    @Context
    protected KeycloakSession session;
    @Context
    protected HttpRequest request;
    protected Auth auth;

    public AbstractSecuredLocalService(RealmModel realm, ClientModel client) {
        this.realm = realm;
        this.client = client;
    }

    @Path("login-redirect")
    @GET
    public Response loginRedirect(@QueryParam("code") String code,
                                  @QueryParam("state") String state,
                                  @QueryParam("error") String error,
                                  @QueryParam("path") String path,
                                  @QueryParam("referrer") String referrer,
                                  @Context HttpHeaders headers) {
        try {
            if (error != null) {
                logger.debug("error from oauth");
                throw new ForbiddenException("error");
            }
            if (path != null && !getValidPaths().contains(path)) {
                throw new BadRequestException("Invalid path");
            }
            if (!realm.isEnabled()) {
                logger.debug("realm not enabled");
                throw new ForbiddenException();
            }
            if (!client.isEnabled()) {
                logger.debug("account management app not enabled");
                throw new ForbiddenException();
            }
            if (code == null) {
                logger.debug("code not specified");
                throw new BadRequestException("code not specified");
            }
            if (state == null) {
                logger.debug("state not specified");
                throw new BadRequestException("state not specified");
            }

            URI uri = getBaseRedirectUri();
            URI redirectUri = path != null ? uri.resolve(path) : uri;
            if (referrer != null) {
                redirectUri = redirectUri.resolve("?referrer=" + referrer);
            }

            return Response.status(302).location(redirectUri).build();
        } finally {
        }
    }

    protected void updateCsrfChecks() {
        Cookie cookie = headers.getCookies().get(AccountService.KEYCLOAK_STATE_CHECKER);
        if (cookie != null) {
            stateChecker = cookie.getValue();
        } else {
            stateChecker = UUID.randomUUID().toString();
            String cookiePath = AuthenticationManager.getRealmCookiePath(realm, uriInfo);
            boolean secureOnly = realm.getSslRequired().isRequired(clientConnection);
            CookieHelper.addCookie(AccountService.KEYCLOAK_STATE_CHECKER, stateChecker, cookiePath, null, null, -1, secureOnly, true);
        }
    }

    protected abstract Set<String> getValidPaths();

    /**
     * Check to see if form post has sessionId hidden field and match it against the session id.
     *
     * @param formData
     */
    protected void csrfCheck(final MultivaluedMap<String, String> formData) {
        if (!auth.isCookieAuthenticated()) return;
        String stateChecker = formData.getFirst("stateChecker");
        if (!this.stateChecker.equals(stateChecker)) {
            throw new ForbiddenException();
        }

    }

    /**
     * Check to see if form post has sessionId hidden field and match it against the session id.
     *
     */
    protected void csrfCheck(String stateChecker) {
        if (!auth.isCookieAuthenticated()) return;
        if (auth.getSession() == null) return;
        if (!this.stateChecker.equals(stateChecker)) {
            throw new ForbiddenException();
        }

    }

    protected abstract URI getBaseRedirectUri();

    protected Response login(String path) {
        OAuthRedirect oauth = new OAuthRedirect();
        String authUrl = OIDCLoginProtocolService.authUrl(uriInfo).build(realm.getName()).toString();
        oauth.setAuthUrl(authUrl);

        oauth.setClientId(client.getClientId());

        UriBuilder uriBuilder = UriBuilder.fromUri(getBaseRedirectUri()).path("login-redirect");

        if (path != null) {
            uriBuilder.queryParam("path", path);
        }

        String referrer = uriInfo.getQueryParameters().getFirst("referrer");
        if (referrer != null) {
            uriBuilder.queryParam("referrer", referrer);
        }

        String referrerUri = uriInfo.getQueryParameters().getFirst("referrer_uri");
        if (referrerUri != null) {
            uriBuilder.queryParam("referrer_uri", referrerUri);
        }

        URI accountUri = uriBuilder.build(realm.getName());

        oauth.setStateCookiePath(accountUri.getRawPath());
        return oauth.redirect(uriInfo, accountUri.toString());
    }

    protected Response authenticateBrowser() {
        AppAuthManager authManager = new AppAuthManager();
        AuthenticationManager.AuthResult authResult = authManager.authenticateIdentityCookie(session, realm);
        if (authResult != null) {
            auth = new Auth(realm, authResult.getToken(), authResult.getUser(), client, authResult.getSession(), true);
        } else {
            return login(null);
        }
        // don't allow cors requests
        // This is to prevent CSRF attacks.
        String requestOrigin = UriUtils.getOrigin(uriInfo.getBaseUri());
        String origin = headers.getRequestHeaders().getFirst("Origin");
        if (origin != null && !requestOrigin.equals(origin)) {
            throw new ForbiddenException();
        }

        if (!request.getHttpMethod().equals("GET")) {
            String referrer = headers.getRequestHeaders().getFirst("Referer");
            if (referrer != null && !requestOrigin.equals(UriUtils.getOrigin(referrer))) {
                throw new ForbiddenException();
            }
        }
        updateCsrfChecks();
        return null;
    }

    static class OAuthRedirect extends AbstractOAuthClient {

        /**
         * closes client
         */
        public void stop() {
        }

        public Response redirect(UriInfo uriInfo, String redirectUri) {
            String state = getStateCode();

            UriBuilder uriBuilder = UriBuilder.fromUri(authUrl)
                    .queryParam(OAuth2Constants.CLIENT_ID, clientId)
                    .queryParam(OAuth2Constants.REDIRECT_URI, redirectUri)
                    .queryParam(OAuth2Constants.STATE, state)
                    .queryParam(OAuth2Constants.RESPONSE_TYPE, OAuth2Constants.CODE);
            if (scope != null) {
                uriBuilder.queryParam(OAuth2Constants.SCOPE, scope);
            }

            URI url = uriBuilder.build();

            // todo httpOnly!
            NewCookie cookie = new NewCookie(getStateCookieName(), state, getStateCookiePath(uriInfo), null, null, -1, isSecure);
            logger.debug("NewCookie: " + cookie.toString());
            logger.debug("Oauth Redirect to: " + url);
            return Response.status(302)
                    .location(url)
                    .cookie(cookie).build();
        }

        private String getStateCookiePath(UriInfo uriInfo) {
            if (stateCookiePath != null) return stateCookiePath;
            return uriInfo.getBaseUri().getRawPath();
        }

    }


}
