package org.adorsys.adkcloak.config;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.adorsys.adkcloak.loader.ClientReprestn;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.util.JsonSerialization;
import org.keycloak.util.KeycloakUriBuilder;

public class RealmClient {
	private static final String masterRealmName = "master";

	@Inject
	private TokenManager tokenManager;
	
    public void createRealm(RealmRepresentation rr) throws Failure {
    	try {
	        HttpPost post = new HttpPost(KeycloakUriBuilder.fromUri(tokenManager.getBaseUrl() + "/auth/admin/realms").build());
	        String asString = JsonSerialization.writeValueAsString(rr);
	        StringEntity stringEntity = new StringEntity(asString);
	        stringEntity.setContentType("application/json");
	        post.setEntity(stringEntity);

	        CloseableHttpResponse response = tokenManager.execute(masterRealmName, post);
	        
            if (response.getStatusLine().getStatusCode() != 201) {
                throw new Failure(response.getStatusLine().getStatusCode());
            }
    	} catch (IOException e) {
			throw new RuntimeException(e);
    	}
    }
    
    public RealmRepresentation findRealm(String realmName) throws Failure {
    	HttpGet get = new HttpGet(KeycloakUriBuilder.fromUri(tokenManager.getBaseUrl() + "/auth/admin/realms/{realm}").build(realmName));
    	return tokenManager.execute(masterRealmName, get, RealmRepresentation.class);
    }
	
	public <T> T findGenericFromRealm(Class<T> klass, String restEndpoint, String realm, String identif) throws Failure{
    	HttpGet get = new HttpGet(KeycloakUriBuilder.fromUri(tokenManager.getBaseUrl() + restEndpoint).build(realm, identif));
    	return tokenManager.execute(masterRealmName, get, klass);
	}

	public ClientReprestn findClient(String realmId, String id) throws Failure {
		return findGenericFromRealm(ClientReprestn.class, "/auth/admin/realms/{realm}/clients/{id}", realmId, id);
	}

	public void createClient(ClientReprestn t)  throws Failure {
    	try {
	        HttpPost post = new HttpPost(KeycloakUriBuilder.fromUri(tokenManager.getBaseUrl() + "/auth/admin/realms/{realm}/clients").build(t.getRealmId()));
	        ClientRepresentation rr = new ClientRepresentation();
	        copy(rr, t);
	        String redirectUriCsStg = t.getRedirectUriCsStg();
	        if(StringUtils.isNotBlank(redirectUriCsStg)){
	        	String[] split = redirectUriCsStg.split(",");
	        	List<String> redirectUris = new ArrayList<String>();
	        	for (String redirectUri : split) {
	        		if(StringUtils.isNotBlank(redirectUri))
	        			redirectUris.add(redirectUri);
				}
				rr.setRedirectUris(redirectUris);
	        }
	        String asString = JsonSerialization.writeValueAsString(rr);
	        StringEntity stringEntity = new StringEntity(asString);
	        stringEntity.setContentType("application/json");
	        post.setEntity(stringEntity);

	        CloseableHttpResponse response = tokenManager.execute(masterRealmName,post);
	        
            if (response.getStatusLine().getStatusCode() != 201) {
            	String reasonPhrase = response.getStatusLine().getReasonPhrase();
                throw new Failure(response.getStatusLine().getStatusCode());
            }
    	} catch (IOException e) {
			throw new RuntimeException(e);
    	}
	}

	public void copy(Object dest, Object orig){
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new IllegalStateException(e);
		}
	}
}
