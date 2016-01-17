package org.adorsys.adkcloak.services.xls;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.adorsys.adcore.xls.CopyUtils;
import org.adorsys.adkcloak.services.utils.KeycloakProperties;
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
import org.keycloak.models.utils.ModelToRepresentation;
import org.keycloak.models.utils.RepresentationToModel;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.services.managers.RealmManager;

public class RealmClient {

	KeycloakProperties properties = KeycloakProperties.singleton();
    KeycloakSession session;
	KeycloakSessionFactory sessionFactory;
	RealmProvider realmProvider;
	UserProvider userProvider;
	String contextPath;
	public RealmClient(KeycloakSessionFactory sessionFactory, String contextPath){
		initDefaultValues();
		this.sessionFactory = sessionFactory;
		this.session = sessionFactory.create();//new KeycloakSessionWrapper();
		this.realmProvider = session.getProvider(RealmProvider.class);
		this.userProvider = session.getProvider(UserProvider.class);
		this.contextPath = contextPath;
	}
	
    private void initDefaultValues(){
    	properties.addProperperty("auth-server-url","http://localhost:8080/auth");
    	properties.addProperperty("use-resource-role-mappings","true");
    	properties.addProperperty("enable-cors","true");
    	properties.addProperperty("cors-max-age","10000");
    	properties.addProperperty("cors-allowed-methods","POST, PUT, DELETE, GET");
    }
	
	public ClientReprestn findClient(String realmId, String clientId) {
        session.getTransaction().begin();
        try {
			ClientModel model = realmProvider.getClientById(clientId, realmProvider.getRealm(realmId));
			if(model==null) return null;
			ClientRepresentation clientRepresentation = ModelToRepresentation.toRepresentation(model);
			
			ClientReprestn res = new ClientReprestn();
			copy(res, clientRepresentation);
			res.setRealmId(realmId);
			return res;
        } finally {
        	session.getTransaction().commit();
        }
	}

	public void createClient(ClientReprestn t) {
        session.getTransaction().begin();
        try {
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
	        RealmModel realm = realmProvider.getRealm(t.getRealmId());
	        ClientModel clientModel = RepresentationToModel.createClient(session, realm, rr, true);
	        String clientSecret = clientModel.getSecret();
	        String credentialKey =clientModel.getClientId() + ".credential";
	        properties.addProperperty(credentialKey, clientSecret);
        } finally {
        	session.getTransaction().commit();
        }
	}

	public ClientRoleReprestn findClientRole(String realmId, String clientId, String name) {
        session.getTransaction().begin();
        try {
	        RealmModel realm = realmProvider.getRealm(realmId);
	        ClientModel clientModel = realm.getClientById(clientId);
	        RoleModel roleModel = clientModel.getRole(name);
	        if(roleModel==null) return null;
	        RoleRepresentation roleRepresentation = ModelToRepresentation.toRepresentation(roleModel);
			if(roleRepresentation==null) return null;
			ClientRoleReprestn res = new ClientRoleReprestn();
			copy(res, roleRepresentation);
			res.setRealmId(realmId);
			res.setClientId(clientId);
			return res;
        } finally {
        	session.getTransaction().commit();
        }
	}

	public void createClientRole(ClientRoleReprestn t) {
        session.getTransaction().begin();
        try {
	        RealmModel realm = realmProvider.getRealm(t.getRealmId());
	        ClientModel clientModel = realm.getClientById(t.getClientId());
			RoleModel role = clientModel.addRole(t.getId(), t.getName());
	        role.setDescription(t.getDescription());
	        boolean scopeParamRequired = t.isScopeParamRequired()==null ? false : t.isScopeParamRequired();
	        role.setScopeParamRequired(scopeParamRequired);
        } finally {
        	session.getTransaction().commit();
        }
	}

	public RealmRepresentation findRealm(String realmId) {
        session.getTransaction().begin();
        try {
	        RealmModel realm = realmProvider.getRealm(realmId);
	        if(realm==null)return null;
	        return ModelToRepresentation.toRepresentation(realm, false);
        } finally {
        	session.getTransaction().commit();
        }
	}

	public RealmRepresentation createRealm(RealmRepresentation t) {
        session.getTransaction().begin();
        try {
	        RealmManager realmManager = new RealmManager(session);
	        realmManager.setContextPath(contextPath);
//	        RealmModel realm = realmManager.createRealm(t.getId(), t.getRealm());
//	        
	        RealmModel realm = realmManager.importRealm(t);
//	        grantPermissionsToRealmAdmin(realm);
	        RealmRepresentation realmRepresentation = ModelToRepresentation.toRepresentation(realm, false);
	        properties.addProperperty("realm-public-key", realmRepresentation.getPublicKey());
	        return realmRepresentation;
        } finally {
        	session.getTransaction().commit();
        }

	}
//    private void grantPermissionsToRealmAdmin(RealmModel realm) {
//    	RealmModel adminRealm = new PatchedRealmManager(session, contextPath).getKeycloakAdminstrationRealm();
//    	UserModel adminUser = userProvider.getUserByUsername("admin", adminRealm);
//    	ClientModel realmAdminApp = adminRealm.getMasterAdminClient();
//    	for (String r : AdminRoles.ALL_REALM_ROLES) {
//    		RoleModel role = realmAdminApp.getRole(r);
//    		adminUser.grantRole(role);
//    	}
//    }

	public RoleRepresentation findClientRoleComposite(String realmId, String compositeRoleId, String childRoleClientId,
			String childRoleName) {
        session.getTransaction().begin();
        try {
	        RealmModel realm = realmProvider.getRealm(realmId);
	        RoleModel compositeRole = realm.getRoleById(compositeRoleId);
	        if(!compositeRole.isComposite()) return null;
	        Set<RoleModel> composites = compositeRole.getComposites();
	        for (RoleModel childRoleModel : composites) {
	        	if(!childRoleModel.getName().equals(childRoleName)) continue;
	        	RoleContainerModel roleContainerModel = childRoleModel.getContainer();
	        	if(roleContainerModel==null) continue;
				if(!(roleContainerModel instanceof ClientModel)) continue;
				ClientModel client = (ClientModel) roleContainerModel;
				if(client.getId().equals(childRoleClientId)){
					return ModelToRepresentation.toRepresentation(childRoleModel);
				}
			}
			return null;
        } finally {
        	session.getTransaction().commit();
        }
	}

	public RoleReprestn findRealmRole(String realmId, String realmRoleName) {
        session.getTransaction().begin();
        try {
	        RealmModel realm = realmProvider.getRealm(realmId);
	        RoleModel roleModel = realm.getRole(realmRoleName);
	        if(roleModel==null) return null;
	        RoleRepresentation roleRepresentation = ModelToRepresentation.toRepresentation(roleModel);
			if(roleRepresentation==null) return null;
			ClientRoleReprestn res = new ClientRoleReprestn();
			copy(res, roleRepresentation);
			res.setRealmId(realmId);
			return res;
        } finally {
        	session.getTransaction().commit();
        }
	}

	public RoleRepresentation findRealmRoleComposite(String realmId, String compositeRoleId, String childRoleName) {
        session.getTransaction().begin();
        try {
	        RealmModel realm = realmProvider.getRealm(realmId);
	        RoleModel compositeRole = realm.getRoleById(compositeRoleId);
	        if(!compositeRole.isComposite()) return null;
	        Set<RoleModel> composites = compositeRole.getComposites();
	        for (RoleModel childRoleModel : composites) {
	        	if(!childRoleModel.getName().equals(childRoleName)) continue;
	        	RoleContainerModel roleContainerModel = childRoleModel.getContainer();
	        	if(roleContainerModel==null) continue;
				if(!(roleContainerModel instanceof RealmModel)) continue;
				RealmModel container = (RealmModel) roleContainerModel;
				if(container.getId().equals(realmId)){
					return ModelToRepresentation.toRepresentation(childRoleModel);
				}
			}
	
			return null;
        } finally {
        	session.getTransaction().commit();
        }
	}

	public void addComposite(String realmId, String compositeRoleId, RoleRepresentation childRole) {
        session.getTransaction().begin();
        try {
	        RealmModel realm = realmProvider.getRealm(realmId);
	        RoleModel compositeRoleModel = realm.getRoleById(compositeRoleId);
	        RoleModel childRoleModel = realm.getRoleById(childRole.getId());
	        compositeRoleModel.addCompositeRole(childRoleModel);
        } finally {
        	session.getTransaction().commit();
        }
	}

	public UserReprestn findUser(String realmId, String username) {
        session.getTransaction().begin();
        try {
	        RealmModel realm = realmProvider.getRealm(realmId);
			UserModel userModel = userProvider.getUserByUsername(username, realm);
			if(userModel==null)return null;
	        UserRepresentation orig = ModelToRepresentation.toRepresentation(userModel);
			UserReprestn res = new UserReprestn();
			copy(res, orig);
			res.setRealmId(realmId);
			return res;
        } finally {
        	session.getTransaction().commit();
        }
	}

	public UserClientRoles findUserClientRole(UserClientRoles ucr) {
        session.getTransaction().begin();
        try {
	        RealmModel realm = realmProvider.getRealm(ucr.getRealmId());
			UserModel userModel = userProvider.getUserByUsername(ucr.getUsername(), realm);
			if(userModel==null) throw new IllegalStateException("No user with username: " + ucr.getUsername());
			ClientModel clientModel = realm.getClientById(ucr.getClientId());
			if(clientModel==null) throw new IllegalStateException("No clinet with id: " + ucr.getClientId());
			Set<RoleModel> clientRoleMappings = userModel.getClientRoleMappings(clientModel);
			for (RoleModel roleModel : clientRoleMappings) {
				if(roleModel.getName().equals(ucr.getRoleName())) return ucr;
				
			}
			return null;
        } finally {
        	session.getTransaction().commit();
        }
	}

	public UserClientRoles addClientRole(UserClientRoles ucr) {
        session.getTransaction().begin();
        try {
	        RealmModel realm = realmProvider.getRealm(ucr.getRealmId());
			UserModel userModel = userProvider.getUserByUsername(ucr.getUsername(), realm);
			if(userModel==null) throw new IllegalStateException("No user with username: " + ucr.getUsername());
			ClientModel clientModel = realm.getClientById(ucr.getClientId());
			if(clientModel==null) throw new IllegalStateException("No clinet with id: " + ucr.getClientId());
			RoleModel roleModel = clientModel.getRole(ucr.getRoleName());
			if(roleModel==null) throw new IllegalStateException("No role with name "+ ucr.getRoleName() +" for client  " + ucr.getClientId());
			userModel.grantRole(roleModel);
			return ucr;
        } finally {
        	session.getTransaction().commit();
        }
	}

	public void addCredential(UserCredentials cred) {
        session.getTransaction().begin();
        try {
	        RealmModel realm = realmProvider.getRealm(cred.getRealmId());
			UserModel userModel = userProvider.getUserByUsername(cred.getUsername(), realm);
			if(userModel==null) throw new IllegalStateException("No user with username: " + cred.getUsername());
	
			UserRepresentation userRep = ModelToRepresentation.toRepresentation(userModel);
			if(userRep.getCredentials()==null)userRep.setCredentials(new ArrayList<CredentialRepresentation>());;
			userRep.getCredentials().add(cred);
			RepresentationToModel.createCredentials(userRep, userModel);
        } finally {
        	session.getTransaction().commit();
        }
	}

	public void createUser(UserReprestn userRep) {
        session.getTransaction().begin();
        try {
	        RealmModel newRealm = realmProvider.getRealm(userRep.getRealmId());
			RepresentationToModel.createUser(session, newRealm, userRep, newRealm.getClientNameMap());
        } finally {
        	session.getTransaction().commit();
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
}
