package org.keycloak.protocol;

import org.codehaus.jackson.annotate.JsonProperty;
import org.jboss.logging.Logger;
import org.keycloak.ClientConnection;
import org.keycloak.jose.jws.JWSBuilder;
import org.keycloak.jose.jws.JWSInput;
import org.keycloak.jose.jws.crypto.HMACProvider;
import org.keycloak.jose.jws.crypto.RSAProvider;
import org.keycloak.models.ClientModel;
import org.keycloak.models.ClientSessionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.services.managers.AuthenticationManager;
import org.keycloak.services.util.CookieHelper;

import javax.crypto.SecretKey;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.UriInfo;
import java.util.HashMap;
import java.util.Map;

/**
 * This is an an encoded token that is stored as a cookie so that if there is a client timeout, then the client session
 * can be restarted.
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class RestartLoginCookie {
    private static final Logger logger = Logger.getLogger(RestartLoginCookie.class);
    public static final String KC_RESTART = "KC_RESTART";
    @JsonProperty("cs")
    protected String clientSession;

    @JsonProperty("cid")
    protected String clientId;

    @JsonProperty("pty")
    protected String authMethod;

    @JsonProperty("ruri")
    protected String redirectUri;

    @JsonProperty("act")
    protected String action;

    @JsonProperty("notes")
    protected Map<String, String> notes = new HashMap<>();

    public String getClientSession() {
        return clientSession;
    }

    public void setClientSession(String clientSession) {
        this.clientSession = clientSession;
    }

    public Map<String, String> getNotes() {
        return notes;
    }

    public void setNotes(Map<String, String> notes) {
        this.notes = notes;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String encode(RealmModel realm) {
        JWSBuilder builder = new JWSBuilder();
        return builder.jsonContent(this)
                .hmac256((SecretKey)realm.getCodeSecretKey());
               //.rsa256(realm.getPrivateKey());

    }

    public RestartLoginCookie() {
    }
    public RestartLoginCookie(ClientSessionModel clientSession) {
        this.action = clientSession.getAction();
        this.clientId = clientSession.getClient().getClientId();
        this.authMethod = clientSession.getAuthMethod();
        this.redirectUri = clientSession.getRedirectUri();
        this.clientSession = clientSession.getId();
        for (Map.Entry<String, String> entry : clientSession.getNotes().entrySet()) {
            notes.put(entry.getKey(), entry.getValue());
        }
    }

    public static void setRestartCookie(RealmModel realm, ClientConnection connection, UriInfo uriInfo, ClientSessionModel clientSession) {
        RestartLoginCookie restart = new RestartLoginCookie(clientSession);
        String encoded = restart.encode(realm);
        int keySize = realm.getCodeSecret().length();
        int size = encoded.length();
        String path = AuthenticationManager.getRealmCookiePath(realm, uriInfo);
        boolean secureOnly = realm.getSslRequired().isRequired(connection);
        CookieHelper.addCookie(KC_RESTART, encoded, path, null, null, -1, secureOnly, true);
    }

    public static void expireRestartCookie(RealmModel realm, ClientConnection connection, UriInfo uriInfo) {
        String path = AuthenticationManager.getRealmCookiePath(realm, uriInfo);
        boolean secureOnly = realm.getSslRequired().isRequired(connection);
        CookieHelper.addCookie(KC_RESTART, "", path, null, null, 0, secureOnly, true);
    }

    public static ClientSessionModel restartSession(KeycloakSession session, RealmModel realm, String code) throws Exception {
        Cookie cook = session.getContext().getRequestHeaders().getCookies().get(KC_RESTART);
        if (cook ==  null) {
            logger.debug("KC_RESTART cookie doesn't exist");
            return null;
        }
        String encodedCookie = cook.getValue();
        JWSInput input = new JWSInput(encodedCookie);
        /*
        if (!RSAProvider.verify(input, realm.getPublicKey())) {
            logger.debug("Failed to verify encoded RestartLoginCookie");
            return null;
        }
        */
        if (!HMACProvider.verify(input, (SecretKey)realm.getCodeSecretKey())) {
            logger.debug("Failed to verify encoded RestartLoginCookie");
            return null;
        }
        RestartLoginCookie cookie = input.readJsonContent(RestartLoginCookie.class);
        String[] parts = code.split("\\.");
        String clientSessionId = parts[1];
        if (!clientSessionId.equals(cookie.getClientSession())) {
            logger.debug("RestartLoginCookie clientSession does not match code's clientSession");
            return null;
        }

        ClientModel client = realm.getClientByClientId(cookie.getClientId());
        if (client == null) return null;

        ClientSessionModel clientSession = session.sessions().createClientSession(realm, client);
        clientSession.setAuthMethod(cookie.getAuthMethod());
        clientSession.setRedirectUri(cookie.getRedirectUri());
        clientSession.setAction(cookie.getAction());
        for (Map.Entry<String, String> entry : cookie.getNotes().entrySet()) {
            clientSession.setNote(entry.getKey(), entry.getValue());
        }

        return clientSession;
    }
}
