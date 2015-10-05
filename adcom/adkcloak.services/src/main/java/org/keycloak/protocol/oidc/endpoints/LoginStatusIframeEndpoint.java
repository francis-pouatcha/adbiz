package org.keycloak.protocol.oidc.endpoints;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;
import org.keycloak.Config;
import org.keycloak.models.ClientModel;
import org.keycloak.models.RealmModel;
import org.keycloak.protocol.oidc.utils.RedirectUtils;
import org.keycloak.util.StreamUtil;
import org.keycloak.util.UriUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class LoginStatusIframeEndpoint {

    @Context
    private UriInfo uriInfo;

    private RealmModel realm;

    public LoginStatusIframeEndpoint(RealmModel realm) {
        this.realm = realm;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getLoginStatusIframe(@QueryParam("client_id") String client_id,
                                         @QueryParam("origin") String origin) {
        if (!UriUtils.isOrigin(origin)) {
            throw new BadRequestException("Invalid origin");
        }

        ClientModel client = realm.getClientByClientId(client_id);
        if (client == null) {
            throw new NotFoundException("could not find client");
        }

        InputStream is = getClass().getClassLoader().getResourceAsStream("login-status-iframe.html");
        if (is == null) throw new NotFoundException("Could not find login-status-iframe.html ");

        boolean valid = false;
        for (String o : client.getWebOrigins()) {
            if (o.equals("*") || o.equals(origin)) {
                valid = true;
                break;
            }
        }

        for (String r : RedirectUtils.resolveValidRedirects(uriInfo, client.getRedirectUris())) {
            int i = r.indexOf('/', 8);
            if (i != -1) {
                r = r.substring(0, i);
            }

            if (r.equals(origin)) {
                valid = true;
                break;
            }
        }

        if (!valid) {
            throw new BadRequestException("Invalid origin");
        }

        try {
            String file = StreamUtil.readString(is);
            file = file.replace("ORIGIN", origin);

            CacheControl cacheControl = new CacheControl();
            cacheControl.setNoTransform(false);
            cacheControl.setMaxAge(Config.scope("theme").getInt("staticMaxAge", -1));

            return Response.ok(file).cacheControl(cacheControl).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
