package org.keycloak.protocol.oidc.utils;

import org.jboss.logging.Logger;
import org.keycloak.models.ClientModel;
import org.keycloak.models.Constants;
import org.keycloak.models.RealmModel;
import org.keycloak.services.Urls;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class RedirectUtils {

    private static final Logger logger = Logger.getLogger(RedirectUtils.class);

    public static String verifyRealmRedirectUri(UriInfo uriInfo, String redirectUri, RealmModel realm) {
        Set<String> validRedirects = getValidateRedirectUris(realm);
        return verifyRedirectUri(uriInfo, redirectUri, realm, validRedirects);
    }

    public static String verifyRedirectUri(UriInfo uriInfo, String redirectUri, RealmModel realm, ClientModel client) {
        Set<String> validRedirects = client.getRedirectUris();
        return verifyRedirectUri(uriInfo, redirectUri, realm, validRedirects);
    }

    public static Set<String> resolveValidRedirects(UriInfo uriInfo, Set<String> validRedirects) {
        // If the valid redirect URI is relative (no scheme, host, port) then use the request's scheme, host, and port
        Set<String> resolveValidRedirects = new HashSet<String>();
        for (String validRedirect : validRedirects) {
            resolveValidRedirects.add(validRedirect); // add even relative urls.
            if (validRedirect.startsWith("/")) {
                validRedirect = relativeToAbsoluteURI(uriInfo, validRedirect);
                logger.debugv("replacing relative valid redirect with: {0}", validRedirect);
                resolveValidRedirects.add(validRedirect);
            }
        }
        return resolveValidRedirects;
    }

    private static Set<String> getValidateRedirectUris(RealmModel realm) {
        Set<String> redirects = new HashSet<String>();
        for (ClientModel client : realm.getClients()) {
            for (String redirect : client.getRedirectUris()) {
                redirects.add(redirect);
            }
        }
        return redirects;
    }

    private static String verifyRedirectUri(UriInfo uriInfo, String redirectUri, RealmModel realm, Set<String> validRedirects) {
        if (redirectUri == null) {
            if (validRedirects.size() != 1) return null;
            String validRedirect = validRedirects.iterator().next();
            int idx = validRedirect.indexOf("/*");
            if (idx > -1) {
                validRedirect = validRedirect.substring(0, idx);
            }
            redirectUri = validRedirect;
        } else if (validRedirects.isEmpty()) {
            logger.debug("No Redirect URIs supplied");
            redirectUri = null;
        } else {
            String r = redirectUri.indexOf('?') != -1 ? redirectUri.substring(0, redirectUri.indexOf('?')) : redirectUri;
            Set<String> resolveValidRedirects = resolveValidRedirects(uriInfo, validRedirects);

            boolean valid = matchesRedirects(resolveValidRedirects, r);

            if (!valid && r.startsWith(Constants.INSTALLED_APP_URL) && r.indexOf(':', Constants.INSTALLED_APP_URL.length()) >= 0) {
                int i = r.indexOf(':', Constants.INSTALLED_APP_URL.length());

                StringBuilder sb = new StringBuilder();
                sb.append(r.substring(0, i));

                i = r.indexOf('/', i);
                if (i >= 0) {
                    sb.append(r.substring(i));
                }

                r = sb.toString();

                valid = matchesRedirects(resolveValidRedirects, r);
            }
            if (valid && redirectUri.startsWith("/")) {
                redirectUri = relativeToAbsoluteURI(uriInfo, redirectUri);
            }
            redirectUri = valid ? redirectUri : null;
        }

        if (Constants.INSTALLED_APP_URN.equals(redirectUri)) {
            return Urls.realmInstalledAppUrnCallback(uriInfo.getBaseUri(), realm.getName()).toString();
        } else {
            return redirectUri;
        }
    }

    private static String relativeToAbsoluteURI(UriInfo uriInfo, String relative) {
        URI baseUri = uriInfo.getBaseUri();
        String uri = baseUri.getScheme() + "://" + baseUri.getHost();
        if (baseUri.getPort() != -1) {
            uri += ":" + baseUri.getPort();
        }
        relative = uri + relative;
        return relative;
    }

    private static boolean matchesRedirects(Set<String> validRedirects, String redirect) {
        for (String validRedirect : validRedirects) {
            if (validRedirect.endsWith("*")) {
                // strip off *
                int length = validRedirect.length() - 1;
                validRedirect = validRedirect.substring(0, length);
                if (redirect.startsWith(validRedirect)) return true;
                // strip off trailing '/'
                if (length - 1 > 0 && validRedirect.charAt(length - 1) == '/') length--;
                validRedirect = validRedirect.substring(0, length);
                if (validRedirect.equals(redirect)) return true;
            } else if (validRedirect.equals(redirect)) return true;
        }
        return false;
    }

}
