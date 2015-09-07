package org.adorsys.adkcloak.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.keycloak.OAuth2Constants;
import org.keycloak.constants.ServiceUrlConstants;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.util.JsonSerialization;
import org.keycloak.util.KeycloakUriBuilder;

@Singleton
public class TokenManager {
	private static final String clientId = "security-admin-console";
	
    private AccessTokenResponse login(String realm) {

    	CloseableHttpClient client = HttpClientBuilder.create().build();

        try {
            HttpPost post = new HttpPost(KeycloakUriBuilder.fromUri(getBaseUrl() + "/auth")
                    .path(ServiceUrlConstants.TOKEN_PATH).build(realm));
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
        
        } catch (IOException e) {
        	throw new IllegalStateException(e);
		} finally {
            try {
				client.close();
			} catch (IOException e) {
	        	throw new IllegalStateException(e);
			}
        }
    }    

    private void logout(String realm, AccessTokenResponse res) throws IOException {

    	CloseableHttpClient client = HttpClientBuilder.create().build();

        try {
            HttpPost post = new HttpPost(KeycloakUriBuilder.fromUri(getBaseUrl() + "/auth")
                    .path(ServiceUrlConstants.TOKEN_SERVICE_LOGOUT_PATH)
                    .build(realm));
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

    public String getBaseUrl() {
    	return "http://localhost:8080";
    }

    public CloseableHttpResponse execute(String realm, HttpUriRequest req){
    	CloseableHttpClient client = HttpClientBuilder.create().build();
    	AccessTokenResponse token = login(realm);
        req.addHeader("Authorization", "Bearer " + token.getToken());
        try {
        	return client.execute(req);
    	} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
    		try {
				client.close();
				logout(realm, token);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
    	}
    }

    public <T> T execute(String realm, HttpUriRequest req, Class<T> klass) throws Failure {
    	CloseableHttpClient client = HttpClientBuilder.create().build();
    	AccessTokenResponse token = login(realm);
        req.addHeader("Authorization", "Bearer " + token.getToken());
        try {
        	CloseableHttpResponse response = client.execute(req);
        	int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 404) {
                return null;// not found
            }
            if (statusCode == 200 ||  statusCode == 304) {
	            HttpEntity entity = response.getEntity();
	            InputStream is = entity.getContent();
	            try {
	            	return JsonSerialization.readValue(is, klass);
	            } finally {
	            	is.close();
	            }
            } else {
                throw new Failure(response.getStatusLine().getStatusCode());
            }
    	} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
    		try {
				client.close();
				logout(realm, token);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
    	}
    }
}
