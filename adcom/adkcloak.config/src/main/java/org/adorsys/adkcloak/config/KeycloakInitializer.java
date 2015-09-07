package org.adorsys.adkcloak.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.keycloak.OAuth2Constants;
import org.keycloak.constants.ServiceUrlConstants;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.util.HostUtils;
import org.keycloak.util.JsonSerialization;
import org.keycloak.util.KeycloakUriBuilder;
import org.keycloak.util.UriUtils;

//@Startup
//@Singleton
public class KeycloakInitializer {
	public static final ObjectMapper mapper = new ObjectMapper();
	private static final String masterRealmName = "master";
	private static final String clientId = "security-admin-console";

    static {
        mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    }
	
	@PostConstruct
	public void postConstruct(){
		try {
			AccessTokenResponse token = login();
			createRealm(token, "adcom");
			logout(token);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Failure e) {
			throw new RuntimeException(e);
		}
	}
	
    public void createRealm(AccessTokenResponse token, String realmId) throws Failure {
    	CloseableHttpClient client = HttpClientBuilder.create().build();
    	try {
	        HttpPost post = new HttpPost(KeycloakUriBuilder.fromUri(getBaseUrl() + "/auth/admin/realms")
	                .build());
	        RealmRepresentation rr = new RealmRepresentation();
	        rr.setRealm(realmId);
	        rr.setEnabled(true);
	        rr.setId(realmId);
	        String asString = JsonSerialization.writeValueAsString(rr);
	        StringEntity stringEntity = new StringEntity(asString);
	        stringEntity.setContentType("application/json");
	        post.setEntity(stringEntity);
	        post.addHeader("Authorization", "Bearer " + token.getToken());
	        CloseableHttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() != 201) {
                throw new Failure(response.getStatusLine().getStatusCode());
            }
	        
    	} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
    		try {
				client.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
    	}
    }

    public AccessTokenResponse login() throws IOException {

    	CloseableHttpClient client = HttpClientBuilder.create().build();

        try {
            HttpPost post = new HttpPost(KeycloakUriBuilder.fromUri(getBaseUrl() + "/auth")
                    .path(ServiceUrlConstants.TOKEN_PATH).build(masterRealmName));
            List <NameValuePair> formparams = new ArrayList <NameValuePair>();
            formparams.add(new BasicNameValuePair("username", "admin"));
            formparams.add(new BasicNameValuePair("password", "admin"));
            formparams.add(new BasicNameValuePair(OAuth2Constants.GRANT_TYPE, "password"));
            formparams.add(new BasicNameValuePair(OAuth2Constants.CLIENT_ID, clientId));
            UrlEncodedFormEntity form = new UrlEncodedFormEntity(formparams, "UTF-8");
            post.setEntity(form);

            HttpResponse response = client.execute(post);
            int status = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (status != 200) {
                String json = getContent(entity);
                throw new IOException("Bad status: " + status + " response: " + json);
            }
            if (entity == null) {
                throw new IOException("No Entity");
            }
            String json = getContent(entity);
            return JsonSerialization.readValue(json, AccessTokenResponse.class);
        } finally {
            client.close();
        }
    }    
    public void logout(AccessTokenResponse res) throws IOException {

    	CloseableHttpClient client = HttpClientBuilder.create().build();

        try {
            HttpPost post = new HttpPost(KeycloakUriBuilder.fromUri(getBaseUrl() + "/auth")
                    .path(ServiceUrlConstants.TOKEN_SERVICE_LOGOUT_PATH)
                    .build(masterRealmName));
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair(OAuth2Constants.REFRESH_TOKEN, res.getRefreshToken()));
            formparams.add(new BasicNameValuePair(OAuth2Constants.CLIENT_ID, clientId));
            UrlEncodedFormEntity form = new UrlEncodedFormEntity(formparams, "UTF-8");
            post.setEntity(form);
            HttpResponse response = client.execute(post);
            boolean status = response.getStatusLine().getStatusCode() != 204;
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return;
            }
            InputStream is = entity.getContent();
            if (is != null) is.close();
            if (status) {
                throw new RuntimeException("failed to logout");
            }
        } finally {
            client.close();;
        }
    }

    static class TypedList extends ArrayList<RoleRepresentation> {
    }

    public static String getContent(HttpEntity entity) throws IOException {
        if (entity == null) return null;
        InputStream is = entity.getContent();
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            int c;
            while ((c = is.read()) != -1) {
                os.write(c);
            }
            byte[] bytes = os.toByteArray();
            String data = new String(bytes);
            return data;
        } finally {
            try {
                is.close();
            } catch (IOException ignored) {

            }
        }

    }
    private static String getBaseUrl(HttpServletRequest request) {
        String useHostname = request.getServletContext().getInitParameter("useHostname");
        if (useHostname != null && "true".equalsIgnoreCase(useHostname)) {
            return "http://" + HostUtils.getHostName() + ":8080";
        } else {
            return UriUtils.getOrigin(request.getRequestURL().toString());
        }
    }
    
    private static String getBaseUrl() {
//    	String hostName = HostUtils.getHostName();
//    	if(hostName==null || hostName.trim().equals(""))hostName = "localhost";
    	return "http://localhost:8080";
    }
    
    public static List<RoleRepresentation> getRealmRoles(HttpServletRequest request, AccessTokenResponse res) throws Failure {

    	CloseableHttpClient client = HttpClientBuilder.create().build();
        try {
            HttpGet get = new HttpGet(getBaseUrl(request) + "/auth/admin/realms/demo/roles");
            get.addHeader("Authorization", "Bearer " + res.getToken());
            try {
                HttpResponse response = client.execute(get);
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new Failure(response.getStatusLine().getStatusCode());
                }
                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();
                try {
                    return JsonSerialization.readValue(is, TypedList.class);
                } finally {
                    is.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
				client.close();
			} catch (IOException e) {
                throw new RuntimeException(e);
			}
        }
    }    

}
