package org.adorsys.adkcloak.services.xls;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adorsys.adcore.env.EnvProperties;
import org.adorsys.adcore.env.PropertiesHolder;
import org.adorsys.adcore.xls.CopyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.models.ClientModel;
import org.keycloak.models.ImpersonationConstants;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.RoleContainerModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserProvider;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.keycloak.models.utils.ModelToRepresentation;
import org.keycloak.models.utils.RepresentationToModel;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.services.managers.RealmManager;

public class RealmClient {

	KeycloakSessionFactory sessionFactory;
	String contextPath;
	PropertiesHolder properties;
	String defaultClientConfigContentTemplate;
	
	public RealmClient(KeycloakSessionFactory sessionFactory, String contextPath){
		String proertyFileName = EnvProperties.getEnvOrSystPropThrowException("IDP_CLIENT_PROPERTIES_FILE");
		this.properties = PropertiesHolder.singleton(proertyFileName);
		this.sessionFactory = sessionFactory;
		this.contextPath = contextPath;
		
		String clientConfigTemplateFileName = EnvProperties.getEnvOrSystPropThrowException("IDP_CLIENT_CONFIG_CONTENT_TEMPLATE");
		try {
			defaultClientConfigContentTemplate =  FileUtils.readFileToString(new File(clientConfigTemplateFileName));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

    private KeycloakSession newSession(){
    	return sessionFactory.create();
    }
	
	private RealmProvider getRealmProvider(KeycloakSession session){
//		return session.getProvider(RealmProvider.class);
		return session.realms();
	}
	private UserProvider getUserProvider(KeycloakSession session){
		return session.getProvider(UserProvider.class);
	}
    
	public ClientReprestn findClient(String realmId, String clientId) {
		KeycloakSession session = newSession();
		try {
			session.getTransaction().begin();
			ClientModel model = loadClient(session, realmId, clientId);
			if(model==null) return null;
			ClientRepresentation clientRepresentation = ModelToRepresentation.toRepresentation(model);
			
			ClientReprestn res = new ClientReprestn();
			copy(res, clientRepresentation);
			res.setRealmId(realmId);
			return res;
		} catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		} finally {
			if(session.getTransaction().isActive() && !session.getTransaction().getRollbackOnly())session.getTransaction().commit();
			session.close();
		}
	}

	public void createClient(ClientReprestn t) {

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
		KeycloakSession session = newSession();
		try {
			session.getTransaction().begin();
			RealmModel realm =  loadRealmThrowException(session, t.getRealmId());
	        ClientModel clientModel = RepresentationToModel.createClient(session, realm, rr, true);
	        String clientSecret = clientModel.getSecret();
	        String credentialKey =clientModel.getClientId() + ".credential";
	        properties.addProperperty(credentialKey, clientSecret);
	        createClientConfigFile(clientModel.getClientId(), clientSecret);
		} catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		} finally {
			if(session.getTransaction().isActive() && !session.getTransaction().getRollbackOnly())session.getTransaction().commit();
			session.close();
		}

	}
	
	private void createClientConfigFile(String clientId, String clientSecret){
		Properties p = properties.toProperties();
		p.put("client.id", clientId); // Useed in ${client.id}
		p.put("CLIENT_ID", clientId); // used at $CLIENT_ID
		p.put("client.secret", clientSecret);
		EnvProperties envProperties = new EnvProperties(p);
		String clientConfigContent = envProperties.resolve(defaultClientConfigContentTemplate);
		String clientConfigFilePath= envProperties.getPropThrowException("IDP_CLIENT_CONFIG_PATH_TEMPLATE");
		try {
			FileUtils.write(new File(clientConfigFilePath), clientConfigContent);
		} catch (IOException e) {
			throw new IllegalStateException("Can not create the client config file at " + clientConfigFilePath);
		}
	}

	public void createClientRole(ClientRoleReprestn t) {
		KeycloakSession session = newSession();
		try {
			if(StringUtils.isBlank(t.getId()))t.setId(KeycloakModelUtils.generateId());

			session.getTransaction().begin();
			ClientModel clientModel = loadClientThrowException(session, t.getRealmId(), t.getClientId());
	        
			RoleModel role = clientModel.addRole(t.getId(), t.getName());
	        role.setDescription(t.getDescription());
	        boolean scopeParamRequired = t.isScopeParamRequired()==null ? false : t.isScopeParamRequired();
	        role.setScopeParamRequired(scopeParamRequired);

		} catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		} finally {
			if(session.getTransaction().isActive() && !session.getTransaction().getRollbackOnly())session.getTransaction().commit();
			session.close();
		}
	}

	public RealmRepresentation findRealm(String realmId) {
		KeycloakSession session = newSession();
		try {
			session.getTransaction().begin();
			RealmProvider realmProvider = getRealmProvider(session);
			RealmModel realm = realmProvider.getRealm(realmId);
	        if(realm==null)return null;
	        return ModelToRepresentation.toRepresentation(realm, false);
		} catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		} finally {
			if(session.getTransaction().isActive() && !session.getTransaction().getRollbackOnly())session.getTransaction().commit();
			session.close();
		}
	}

	static class MyRealmManager extends RealmManager{

		public MyRealmManager(KeycloakSession session, RealmProvider rp) {
			super(session);
			model = rp;
		}
	}
	
	public RealmRepresentation createRealm(RealmRepresentation t) {
		KeycloakSession session = newSession();
		try {
			session.getTransaction().begin();
//	        RealmManager realmManager = new MyRealmManager(new MyKeycloakSession(session), getRealmProvider(session));
			RealmManager realmManager = new RealmManager(session);
	        realmManager.setContextPath(contextPath);
	        
	//	        RealmModel realm = realmManager.createRealm(t.getId(), t.getRealm());
	//	        
	        RealmModel realm = realmManager.importRealm(t);
	//	        grantPermissionsToRealmAdmin(realm);
	        RealmRepresentation realmRepresentation = ModelToRepresentation.toRepresentation(realm, false);
	        properties.addProperperty("realm-public-key", realmRepresentation.getPublicKey());

	        return realmRepresentation;
		} catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		} finally {
			if(session.getTransaction().isActive() && !session.getTransaction().getRollbackOnly())session.getTransaction().commit();
			session.close();
		}

	}
	public ClientRoleReprestn findClientRole(String realmId, String clientId, String roleName) {
		KeycloakSession session = newSession();
		try {
			session.getTransaction().begin();
			ClientModel clientModel = loadClientThrowException(session, realmId, clientId);
	        RoleModel roleModel = clientModel.getRole(roleName);
	        if(roleModel==null) return null;
	        RoleRepresentation roleRepresentation = ModelToRepresentation.toRepresentation(roleModel);
			if(roleRepresentation==null) return null;
			ClientRoleReprestn res = new ClientRoleReprestn();
			copy(res, roleRepresentation);
			res.setRealmId(realmId);
			res.setClientId(clientId);
			return res;
		} catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		} finally {
			if(session.getTransaction().isActive() && !session.getTransaction().getRollbackOnly())session.getTransaction().commit();
			session.close();
		}
	}	
	public RoleReprestn findRealmRole(String realmId, String realmRoleName) {
		KeycloakSession session = newSession();
		try {
			session.getTransaction().begin();
			RoleModel roleModel = loadRealmRole(session, realmId, realmRoleName);
	        if(roleModel==null) return null;
	        
	        RoleRepresentation roleRepresentation = ModelToRepresentation.toRepresentation(roleModel);
			if(roleRepresentation==null) return null;
			ClientRoleReprestn res = new ClientRoleReprestn();
			copy(res, roleRepresentation);
			res.setRealmId(realmId);
			return res;
		} catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		} finally {
			if(session.getTransaction().isActive() && !session.getTransaction().getRollbackOnly())session.getTransaction().commit();
			session.close();
		}
	}

	public RoleModel findChildRoleFromComposite(RolesReprestn rolesRep) {
		KeycloakSession session = newSession();
		try {
			session.getTransaction().begin();
			RoleModel compositeRole = null;
			if(StringUtils.isBlank(rolesRep.getCompositeRoleClientId())){
				compositeRole =loadRealmRoleThrowException(session, rolesRep.getRealmId(), rolesRep.getCompositeRoleName());
			} else {
				compositeRole =loadClientRoleThrowException(session, rolesRep.getRealmId(), rolesRep.getCompositeRoleClientId(), rolesRep.getCompositeRoleName());
			}

	        Set<RoleModel> composites = compositeRole.getComposites();
	        if(composites==null) return null;
	        for (RoleModel childRoleModel : composites) {
	        	// First make sure name match.
	        	if(!childRoleModel.getName().equals(rolesRep.getChildRoleName())) continue;
	        	
	        	RoleContainerModel roleContainerModel = childRoleModel.getContainer();
	        	if(roleContainerModel==null) continue;
	        	
	        	if(!StringUtils.isBlank(rolesRep.getChildRoleClientId())){// Look for client child role
	        		if((roleContainerModel instanceof ClientModel)) continue;
					ClientModel client = (ClientModel) roleContainerModel;
					if(client.getClientId().equals(rolesRep.getChildRoleClientId())) return childRoleModel;
	        	} else {// look for realm role.
	        		return childRoleModel;
	        	}
			}
			return null;
		} catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		} finally {
			if(session.getTransaction().isActive() && !session.getTransaction().getRollbackOnly())session.getTransaction().commit();
			session.close();
		}
	}

	public void addComposite(RolesReprestn rolesRep) {
		KeycloakSession session = newSession();
		try {
			session.getTransaction().begin();
			RoleModel compositeRoleModel = loadCompositeRoleThrowException(session, rolesRep);
	        RoleModel childRoleModel = loadChildRoleThrowException(session, rolesRep);
	        compositeRoleModel.addCompositeRole(childRoleModel);
		} catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		} finally {
			if(session.getTransaction().isActive() && !session.getTransaction().getRollbackOnly())session.getTransaction().commit();
			session.close();
		}
	}

	public UserReprestn findUser(String realmId, String username) {
		KeycloakSession session = newSession();
		try {
			session.getTransaction().begin();
			RealmProvider realmProvider = getRealmProvider(session);
			RealmModel realm = realmProvider.getRealm(realmId);
			UserModel userModel = getUserProvider(session).getUserByUsername(username, realm);
			if(userModel==null)return null;
	        UserRepresentation orig = ModelToRepresentation.toRepresentation(userModel);
			UserReprestn res = new UserReprestn();
			copy(res, orig);
			res.setRealmId(realmId);
			return res;
		} catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		} finally {
			if(session.getTransaction().isActive() && !session.getTransaction().getRollbackOnly())session.getTransaction().commit();
			session.close();
		}
	}

	public UserClientRoles findUserClientRole(UserClientRoles ucr) {
		KeycloakSession session = newSession();
		try {
			session.getTransaction().begin();
			UserModel userModel = loadUserThrowException(session, ucr.getRealmId(), ucr.getUsername());
			ClientModel clientModel = loadClientThrowException(session, ucr.getClientRealmId(), ucr.getClientId());
			Set<RoleModel> clientRoleMappings = userModel.getClientRoleMappings(clientModel);
			for (RoleModel roleModel : clientRoleMappings) {
				if(roleModel.getName().equals(ucr.getRoleName())) return ucr;
				
			}
			return null;
		} catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		} finally {
			if(session.getTransaction().isActive() && !session.getTransaction().getRollbackOnly())session.getTransaction().commit();
			session.close();
		}
	}

	public UserClientRoles addClientRole(UserClientRoles ucr) {
		KeycloakSession session = newSession();
		try {
			session.getTransaction().begin();
			UserModel userModel = loadUserThrowException(session, ucr.getRealmId(), ucr.getUsername());
			ClientModel clientModel = loadClientThrowException(session, ucr.getClientRealmId(), ucr.getClientId());
			RoleModel roleModel = clientModel.getRole(ucr.getRoleName());
			if(roleModel==null) throw new IllegalStateException("No role with name "+ ucr.getRoleName() +" for client  " + ucr.getClientId() + " of relam " + clientModel.getRealm().getName());
			userModel.grantRole(roleModel);
			session.getTransaction().commit();
			return ucr;
		} catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		} finally {
			if(session.getTransaction().isActive() && !session.getTransaction().getRollbackOnly())session.getTransaction().commit();
			session.close();
		}
	}
	public UserRealmRoles findUserRealmRole(UserRealmRoles urr) {
		KeycloakSession session = newSession();
		try {
			session.getTransaction().begin();
			UserModel userModel = loadUserThrowException(session, urr.getRealmId(), urr.getUsername());

			RealmProvider realmProvider = getRealmProvider(session);
			RealmModel roleRealm = realmProvider.getRealm(urr.getRoleRealmId());
			Set<RoleModel> realmRoleMappings = userModel.getRealmRoleMappings();
			for (RoleModel roleModel : realmRoleMappings) {
				RoleModel roleFromRealm = roleRealm.getRole(urr.getRoleName());
				if(roleFromRealm==null) continue;
				if(roleModel.getId().equals(roleFromRealm.getId())) return urr;
			}
			return null;
		} catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		} finally {
			if(session.getTransaction().isActive() && !session.getTransaction().getRollbackOnly())session.getTransaction().commit();
			session.close();
		}
	}

	public UserRealmRoles addRealmRole(UserRealmRoles urr) {
		KeycloakSession session = newSession();
		try {
			session.getTransaction().begin();
			UserModel userModel = loadUserThrowException(session, urr.getRealmId(), urr.getUsername());

			RealmProvider realmProvider = getRealmProvider(session);
			RealmModel roleRealm = realmProvider.getRealm(urr.getRoleRealmId());
			RoleModel roleModel = roleRealm.getRole(urr.getRoleName());
			if(roleModel==null)throw new IllegalStateException("No role with name "+ urr.getRoleName() +" in realm  " + roleRealm.getName());
			userModel.grantRole(roleModel);
			session.getTransaction().commit();
			return urr;
		} catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		} finally {
			if(session.getTransaction().isActive() && !session.getTransaction().getRollbackOnly())session.getTransaction().commit();
			session.close();
		}
	}

	public void addCredential(UserCredentials cred) {
		KeycloakSession session = newSession();
		try {
			session.getTransaction().begin();
			RealmProvider realmProvider = getRealmProvider(session);
			RealmModel realm = realmProvider.getRealm(cred.getRealmId());
			UserModel userModel = getUserProvider(session).getUserByUsername(cred.getUsername(), realm);
			if(userModel==null) throw new IllegalStateException("No user with username: " + cred.getUsername());
	
			UserRepresentation userRep = ModelToRepresentation.toRepresentation(userModel);
			if(userRep.getCredentials()==null)userRep.setCredentials(new ArrayList<CredentialRepresentation>());;
			userRep.getCredentials().add(cred);
			RepresentationToModel.createCredentials(userRep, userModel);

		} catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		} finally {
			if(session.getTransaction().isActive() && !session.getTransaction().getRollbackOnly())session.getTransaction().commit();
			session.close();
		}
	}

	public void createUser(UserReprestn userRep) {
		KeycloakSession session = newSession();
		try {
			session.getTransaction().begin();
			RealmProvider realmProvider = getRealmProvider(session);
	
			RealmModel newRealm = realmProvider.getRealm(userRep.getRealmId());
			RepresentationToModel.createUser(session, newRealm, userRep, newRealm.getClientNameMap());

		} catch(RuntimeException ex){
			session.getTransaction().rollback();
			throw ex;
		} finally {
			if(session.getTransaction().isActive() && !session.getTransaction().getRollbackOnly())session.getTransaction().commit();
			session.close();
		}
	}

	public void copy(Object dest, Object orig){
		CopyUtils.copy(dest, orig);
	}
	
	static class PatchedRealmManager extends RealmManager{

		public PatchedRealmManager(KeycloakSession session, String contextPath) {
			super(session);
			model = session.getProvider(RealmProvider.class);
			setContextPath(contextPath);
		}
		@Override
	    public void setupImpersonationService(RealmModel realm) {
			ImpersonationConstants.setupMasterRealmRole(session.getProvider(RealmProvider.class), realm);
			ImpersonationConstants.setupRealmRole(realm);
	    }
		
	}
	
	public UserModel loadUser(KeycloakSession session, String realmId, String username){
		RealmModel realm = loadRealmThrowException(session, realmId);
		return getUserProvider(session).getUserByUsername(username, realm);
	}
	public UserModel loadUserThrowException(KeycloakSession session, String realmId, String username){
		UserModel userModel = loadUser(session, realmId, username);
		if(userModel==null) throw new IllegalStateException("No user with username: " + username + " in realm with id " + realmId);
		return userModel;
	}
	
	private ClientModel loadClient(KeycloakSession session, String realmId, String clientId){
		RealmModel realm = loadRealmThrowException(session, realmId);
		return realm.getClientByClientId(clientId);
	}
	private ClientModel loadClientThrowException(KeycloakSession session, String realmId, String clientId){
		ClientModel clientModel = loadClient(session, realmId, clientId);
		if(clientModel==null) throw new IllegalStateException("No client with clientId: " + clientId + " in realm with id " + realmId);
		return clientModel;
	}
	private RealmModel loadRealmThrowException(KeycloakSession session, String realmId){
		RealmProvider realmProvider = getRealmProvider(session);
		RealmModel realm = realmProvider.getRealm(realmId);
		if(realm==null) throw new IllegalStateException("No realm with id: " + realmId);
		return realm;
	}

	private RoleModel loadClientRole(KeycloakSession session, String realmId, String clientId, String roleName){
		ClientModel clientModel = loadClientThrowException(session, realmId, clientId);
		return clientModel.getRole(roleName);
	}
	private RoleModel loadClientRoleThrowException(KeycloakSession session, String realmId, String clientId, String roleName){
		RoleModel roleModel = loadClientRole(session, realmId, clientId, roleName);
		if(roleModel==null) throw new IllegalStateException("No role with role name "+ roleName+ " in client with clientId " + clientId + " of realm with id " + realmId);
		return roleModel;
	}

	private RoleModel loadRealmRole(KeycloakSession session, String realmId, String roleName){
		RealmModel realmModel = loadRealmThrowException(session, realmId);
		return realmModel.getRole(roleName);
	}
	private RoleModel loadRealmRoleThrowException(KeycloakSession session, String realmId, String roleName){
		RoleModel roleModel = loadRealmRole(session, realmId, roleName);
		if(roleModel==null) throw new IllegalStateException("No role with role name "+ roleName+ " in realm with id " + realmId);
		return roleModel;
	}
	
	private RoleModel loadChildRoleThrowException(KeycloakSession session, RolesReprestn rolesRep){
		if(StringUtils.isBlank(rolesRep.getChildRoleClientId()))
			return loadRealmRoleThrowException(session, rolesRep.getRealmId(), rolesRep.getChildRoleName());

		return loadClientRoleThrowException(session, rolesRep.getRealmId(), rolesRep.getChildRoleClientId(), rolesRep.getChildRoleName());
	}

	private RoleModel loadCompositeRoleThrowException(KeycloakSession session, RolesReprestn rolesRep){
		
		if(StringUtils.isBlank(rolesRep.getCompositeRoleClientId()))
			return loadRealmRoleThrowException(session, rolesRep.getRealmId(), rolesRep.getCompositeRoleName());

		return loadClientRoleThrowException(session, rolesRep.getRealmId(), rolesRep.getCompositeRoleClientId(), rolesRep.getCompositeRoleName());
	}
}
