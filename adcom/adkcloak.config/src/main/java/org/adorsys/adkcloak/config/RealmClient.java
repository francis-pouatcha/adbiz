package org.adorsys.adkcloak.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adkcloak.loader.ClientReprestn;
import org.adorsys.adkcloak.loader.ClientRoleReprestn;
import org.adorsys.adkcloak.loader.RoleReprestn;
import org.adorsys.adkcloak.loader.UserClientRoles;
import org.adorsys.adkcloak.loader.UserCredentials;
import org.adorsys.adkcloak.loader.UserReprestn;
import org.adorsys.adkcloak.utils.CopyUtils;
import org.adorsys.adkcloak.utils.KeycloakProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.util.JsonSerialization;
import org.keycloak.util.KeycloakUriBuilder;

public class RealmClient {
	private static final String masterRealmName = "master";

	@Inject
	private TokenManager tokenManager;
	
	@Inject
	private KeycloakProperties properties;
	
	@PostConstruct
	public void postConstruct(){
		initDefaultValues();
	}
	
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
            
            RealmRepresentation realmRepresentation = findRealm(rr.getRealm());
            properties.addProperperty("realm-public-key", realmRepresentation.getPublicKey());
            
    	} catch (IOException e) {
			throw new RuntimeException(e);
    	}
    }
    
    private void initDefaultValues(){
    	properties.addProperperty("auth-server-url","http://localhost:8080/auth");
    	properties.addProperperty("use-resource-role-mappings","true");
    	properties.addProperperty("enable-cors","true");
    	properties.addProperperty("cors-max-age","10000");
    	properties.addProperperty("cors-allowed-methods","POST, PUT, DELETE, GET");
    }
    
    public RealmRepresentation findRealm(String realmName) throws Failure {
    	HttpGet get = new HttpGet(KeycloakUriBuilder.fromUri(tokenManager.getBaseUrl() + "/auth/admin/realms/{realm}").build(realmName));
    	return tokenManager.execute(masterRealmName, get, RealmRepresentation.class);
    }
	
	public <T> T findGenericFromRealm(Class<T> klass, String restEndpoint, Object... params) throws Failure{
    	HttpGet get = new HttpGet(KeycloakUriBuilder.fromUri(tokenManager.getBaseUrl() + restEndpoint).build(params));
    	return tokenManager.execute(masterRealmName, get, klass);
	}

	public ClientReprestn findClient(String realmId, String id) throws Failure {
		ClientRepresentation orig = findGenericFromRealm(ClientRepresentation.class, "/auth/admin/realms/{realm}/clients/{id}", realmId, id);
		if(orig==null) return null;
		ClientReprestn res = new ClientReprestn();
		copy(res, orig);
		res.setRealmId(realmId);
		return res;
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

	        tokenManager.execute(masterRealmName,post, 201);
	                    
	        String clientId = t.getClientId();
//            ClientReprestn clientReprestn = findClient(t.getRealmId(), clientId);
//            String secret = clientReprestn.getSecret();
	        CredentialRepresentation secret = findGenericFromRealm(CredentialRepresentation.class, "/auth/admin/realms/{realm}/clients/{id}/client-secret", t.getRealmId(), clientId);
            String credentialKey =clientId + ".credential";
            properties.addProperperty(credentialKey, secret.getValue());
    	} catch (IOException e) {
			throw new RuntimeException(e);
    	}
	}

	public void copy(Object dest, Object orig){
		CopyUtils.copy(dest, orig);
	}

	public void createClientRole(ClientRoleReprestn t) throws Failure {
    	try {
	        HttpPost post = new HttpPost(KeycloakUriBuilder.fromUri(tokenManager.getBaseUrl() + "/auth/admin/realms/{realmId}/clients/{clientId}/roles").build(t.getRealmId(), t.getClientId()));
	        RoleRepresentation rr = new RoleRepresentation();
	        copy(rr, t);
//	        String compisitesId = t.getCompositesId();
	        // TODO find or create composite
	        String roleId = t.getRealmId() + "." + t.getName();
	        t.setId(roleId);
	        String asString = JsonSerialization.writeValueAsString(rr);
	        StringEntity stringEntity = new StringEntity(asString);
	        stringEntity.setContentType("application/json");
	        post.setEntity(stringEntity);

	        CloseableHttpResponse response = tokenManager.execute(masterRealmName,post);
	        
            if (response.getStatusLine().getStatusCode() != 201) {
                throw new Failure(response.getStatusLine().getStatusCode());
            }
    	} catch (IOException e) {
			throw new RuntimeException(e);
    	}
	}

	public ClientRoleReprestn findClientRole(String realmId, String clientId, String roleName) throws Failure {
		RoleRepresentation orig = findRawClientRole(realmId, clientId, roleName);
		if(orig==null) return null;
		ClientRoleReprestn res = new ClientRoleReprestn();
		copy(res, orig);
		res.setRealmId(realmId);
		res.setClientId(clientId);
		return res;
	}
	private RoleRepresentation findRawClientRole(String realmId, String clientId, String roleName) throws Failure {
		return findGenericFromRealm(RoleRepresentation.class, "/auth/admin/realms/{realmId}/clients/{clientId}/roles/{roleName}", realmId, clientId, roleName);
	}

	public RoleReprestn findRealmRole(String realmId, String roleName) throws Failure {
		RoleRepresentation orig = findGenericFromRealm(RoleRepresentation.class, "/auth/admin/realms/{realmId}/roles/{roleName}", realmId, roleName);
		if(orig==null) return null;
		RoleReprestn res = new RoleReprestn();
		copy(res, orig);
		res.setRealmId(realmId);
		return res;
	}

	public RoleRepresentation findClientRoleComposite(String realmId, String compositeRoleId,
			String childRoleClientId, String childRoleName) throws Failure {
		
		RoleRepresentation[] orig = findGenericFromRealm(RoleRepresentation[].class, "/auth/admin/realms/{realmId}/roles-by-id/{compositeRoleId}/composites", realmId, compositeRoleId);
		if(orig==null || orig.length==0) return null;
		for (RoleRepresentation roleRepresentation : orig) {
			if(!StringUtils.equalsIgnoreCase(childRoleName, roleRepresentation.getName())) return roleRepresentation;
		}
		return null;
	}

	public RoleRepresentation findRealmRoleComposite(String realmId, String compositeRoleId,
			String childRoleName) throws Failure {
		RoleRepresentation[] orig = findGenericFromRealm(RoleRepresentation[].class, "http://localhost:8080/auth/admin/realms/{realmId}/roles-by-id/{compositeRoleId}/composites/realm", realmId, compositeRoleId);
		if(orig==null || orig.length==0) return null;
		for (RoleRepresentation roleRepresentation : orig) {
			if(StringUtils.equalsIgnoreCase(childRoleName, roleRepresentation.getName())) return roleRepresentation;
		}
		return null;
	}

	public void addComposite(String realmId, String compositeRoleId,
			RoleRepresentation childRole) throws Failure 
	{
    	try {
    		
	        HttpPost post = new HttpPost(KeycloakUriBuilder.fromUri(tokenManager.getBaseUrl() + "/auth/admin/realms/{realmId}/roles-by-id/{compositeRoleId}/composites").build(realmId, compositeRoleId));

	        RoleRepresentation rr = new RoleRepresentation();
	        copy(rr, childRole);
	        List<RoleRepresentation> roles = Arrays.asList(rr); 
	        String asString = JsonSerialization.writeValueAsString(roles);
	        StringEntity stringEntity = new StringEntity(asString);
	        stringEntity.setContentType("application/json");
	        post.setEntity(stringEntity);

	        CloseableHttpResponse response = tokenManager.execute(masterRealmName,post);
	        
            if (response.getStatusLine().getStatusCode() != 204) {
                throw new Failure(response.getStatusLine().getStatusCode());
            }
    	} catch (IOException e) {
			throw new RuntimeException(e);
    	}
	}

	public UserReprestn findUser(String realmId, String username) throws Failure {
    	UserRepresentation orig = findRawUser(realmId, username);
    	if(orig==null) return null;
		UserReprestn res = new UserReprestn();
		copy(res, orig);
		res.setRealmId(realmId);
		return res;
	}
	private UserRepresentation findRawUser(String realmId, String username) throws Failure {
    	HttpGet get = new HttpGet(KeycloakUriBuilder.fromUri(tokenManager.getBaseUrl() + "/auth/admin/realms/{realmId}/users")
    			.queryParam("username", username).build(realmId));
    	UserRepresentation[] result = tokenManager.execute(masterRealmName, get, UserRepresentation[].class);
    	if(result==null || result.length<1) return null;
    	return result[0];
	}

	public void createUser(UserReprestn t) throws Failure {
    	try {
	        HttpPost post = new HttpPost(KeycloakUriBuilder.fromUri(tokenManager.getBaseUrl() + "/auth/admin/realms/{realmId}/users").build(t.getRealmId()));
	        UserRepresentation rr = new UserRepresentation();
	        copy(rr, t);
	        String asString = JsonSerialization.writeValueAsString(rr);
	        StringEntity stringEntity = new StringEntity(asString);
	        stringEntity.setContentType("application/json");
	        post.setEntity(stringEntity);

	        CloseableHttpResponse response = tokenManager.execute(masterRealmName,post);
	        
            if (response.getStatusLine().getStatusCode() != 201) {
                throw new Failure(response.getStatusLine().getStatusCode());
            }
    	} catch (IOException e) {
			throw new RuntimeException(e);
    	}
	}

	public void addCredential(UserCredentials t) throws Failure {
    	UserRepresentation orig = findRawUser(t.getRealmId(), t.getUsername());

		if(orig==null) throw new IllegalStateException("No user with username: " + t.getUsername());
		List<CredentialRepresentation> credentials = orig.getCredentials();
		if(credentials==null){
			credentials = new ArrayList<CredentialRepresentation>();
			orig.setCredentials(credentials);
		}
		CredentialRepresentation cred = null;
		for (CredentialRepresentation cr : credentials) {
			if(StringUtils.equals(cr.getType(), t.getType())){
				cred = cr;
				break;
			}
		}
		if(cred==null) {
			cred = new CredentialRepresentation();
			credentials.add(cred);
		}
		CopyUtils.copy(cred, t);
		
    	try {
    		HttpPut put = new HttpPut(KeycloakUriBuilder.fromUri(tokenManager.getBaseUrl() + "/auth/admin/realms/{realmId}/users/{userid}/reset-password").build(t.getRealmId(), orig.getId()));
	        String asString = JsonSerialization.writeValueAsString(cred);
	        StringEntity stringEntity = new StringEntity(asString);
	        stringEntity.setContentType("application/json");
	        put.setEntity(stringEntity);

	        CloseableHttpResponse response = tokenManager.execute(masterRealmName,put);
	        
            if (response.getStatusLine().getStatusCode() != 204) {
                throw new Failure(response.getStatusLine().getStatusCode());
            }
    	} catch (IOException e) {
			throw new RuntimeException(e);
    	}
	}

	public void addClientRole(UserClientRoles t) throws Failure {
		
		RoleRepresentation clientRole = findRawClientRole(t.getRealmId(), t.getClientId(), t.getRoleName());
		if(clientRole==null)throw new IllegalStateException("No client with role name: " + t.getRoleName());
		
    	UserRepresentation orig = findRawUser(t.getRealmId(), t.getUsername());

		if(orig==null) throw new IllegalStateException("No user with username: " + t.getUsername());
						
    	try {
    		HttpPost post = new HttpPost(KeycloakUriBuilder.fromUri(tokenManager.getBaseUrl() + "/auth/admin/realms/{realmId}/users/{userid}/role-mappings/clients/{clientId}").build(t.getRealmId(), orig.getId(), t.getClientId()));
    		clientRole.setComposites(null);
    		RoleRepresentation[] clientRoles = new RoleRepresentation[]{clientRole};
	        String asString = JsonSerialization.writeValueAsString(clientRoles);
	        StringEntity stringEntity = new StringEntity(asString);
	        stringEntity.setContentType("application/json");
	        post.setEntity(stringEntity);

	        CloseableHttpResponse response = tokenManager.execute(masterRealmName,post);
	        
            if (response.getStatusLine().getStatusCode() != 204) {
                throw new Failure(response.getStatusLine().getStatusCode());
            }
    	} catch (IOException e) {
			throw new RuntimeException(e);
    	}
	}

	public UserClientRoles findUserClientRole(UserClientRoles ucr) throws Failure {
    	UserRepresentation orig = findRawUser(ucr.getRealmId(), ucr.getUsername());
		if(orig==null) throw new IllegalStateException("No user with username: " + ucr.getUsername());
		
    	HttpGet get = new HttpGet(KeycloakUriBuilder.fromUri(tokenManager.getBaseUrl() + "/admin/realms/{realm}/users/{id}/role-mappings/clients/{client}")
    			.build(ucr.getRealmId(), orig.getId(), ucr.getClientId()));
    	ClientRoleReprestn[] result = tokenManager.execute(masterRealmName, get, ClientRoleReprestn[].class);
    	if(result==null || result.length<1) return null;
    	for (ClientRoleReprestn clientRoleReprestn : result) {
			if(StringUtils.equals(clientRoleReprestn.getName(), ucr.getRoleName())){
				return ucr;
			}
		}
    	return null;
	}
}
