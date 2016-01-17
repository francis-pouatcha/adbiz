package org.adorsys.adkeycloak.model.b2.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.adorsys.adkeycloak.model.b2.jpa.B2FederatedIdentityEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2GroupMemberEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserConsentEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserConsentGrantedProtMapper;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserConsentGrantedRole;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserRoleEntity;
import org.adorsys.adkeycloak.model.b2.repo.FederatedIdentityStore;
import org.adorsys.adkeycloak.model.b2.repo.GroupMemberStore;
import org.adorsys.adkeycloak.model.b2.repo.UserAttributeStore;
import org.adorsys.adkeycloak.model.b2.repo.UserConsentGrantedProtMapperStore;
import org.adorsys.adkeycloak.model.b2.repo.UserConsentGrantedRoleStore;
import org.adorsys.adkeycloak.model.b2.repo.UserConsentStore;
import org.adorsys.adkeycloak.model.b2.repo.UserCredentialStore;
import org.adorsys.adkeycloak.model.b2.repo.UserRequiredActionStore;
import org.adorsys.adkeycloak.model.b2.repo.UserRoleStore;
import org.adorsys.adkeycloak.model.b2.repo.UserStore;
import org.keycloak.models.ClientModel;
import org.keycloak.models.CredentialValidationOutput;
import org.keycloak.models.FederatedIdentityModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.RequiredActionProviderModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserFederationProviderModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserProvider;
import org.keycloak.models.entities.FederatedIdentityEntity;
import org.keycloak.models.utils.CredentialValidation;
import org.keycloak.models.utils.KeycloakModelUtils;

public class B2UserProvider implements UserProvider {

    private final KeycloakSession session;
    private final UserStore userStore;
    private final UserConsentStore userConsentStore;
    private final GroupMemberStore groupMemberStore;
    private final FederatedIdentityStore federatedIdentityStore;
    private final UserAttributeStore userAttributeStore;
    private final UserRoleStore userRoleStore;
    private final UserConsentGrantedProtMapperStore userConsentGrantedProtMapperStore;
    private final UserConsentGrantedRoleStore userConsentGrantedRoleStore;
    private final UserRequiredActionStore userRequiredActionStore;
    private final UserCredentialStore userCredentialStore;

    public B2UserProvider(KeycloakSession session, EntityManager em) {
        this.session = session;
        this.userStore = new UserStore(this, em, 10000);
        this.userRoleStore = new UserRoleStore(this, em, 100000);
        this.groupMemberStore = new GroupMemberStore(this, em, 100000);
        this.federatedIdentityStore = new FederatedIdentityStore(this, em, 40000);
        this.userAttributeStore = new UserAttributeStore(this, em, 40000);
        this.userRequiredActionStore = new UserRequiredActionStore(this, em, 20000); 

        this.userConsentStore = new UserConsentStore(this, em, 40000);
        this.userConsentGrantedProtMapperStore = new UserConsentGrantedProtMapperStore(this, em, 400000);
        this.userConsentGrantedRoleStore = new UserConsentGrantedRoleStore(this, em, 400000);
        this.userCredentialStore = new UserCredentialStore(this, em, 20000);
        
    }
    
    private RealmProvider getNativeRealmProvider(){
        // Check for and load the native realm provider if any,
        return session.getProvider(RealmProvider.class);
    }

    @Override
    public void close() {
    }

    @Override
    public UserAdapter getUserById(String id, RealmModel realm) {
        B2UserEntity user = userStore.load(id);

        // Check that it's user from this realm
        if (user == null || !realm.getId().equals(user.getRealmId())) {
            return null;
        } else {
            return new UserAdapter(session, realm, user, userStore);
        }
    }

    @Override
    public UserModel getUserByUsername(String username, RealmModel realm) {
    	B2UserEntity user = userStore.findByRealmIdAndUsername(realm.getId(), username);
        if (user == null) {
            return null;
        } else {
            return new UserAdapter(session, realm, user, userStore);
        }
    }

    @Override
    public UserModel getUserByEmail(String email, RealmModel realm) {
    	B2UserEntity user = userStore.findByRealmIdAndEmail(realm.getId(), email);
        if (user == null) {
            return null;
        } else {
            return new UserAdapter(session, realm, user, userStore);
        }
    }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group, int firstResult, int maxResults) {
        List<String> userIds = getGroupMemberStore().findUserIdsByRealmIdAndGroupIdOrderByUserName(realm.getId(), group.getId(), firstResult, maxResults);
        List<B2UserEntity> users = 	new ArrayList<B2UserEntity>();
        for (String userId : userIds) {
        	users.add(getUserStore().load(userId));
		}
        return convertUserEntities(realm, users);
    }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group) {
    	Long count = getGroupMemberStore().countByRealmIdAndGroupId(realm.getId(), group.getId());
        return getGroupMembers(realm, group, 0, count.intValue());
    }

    @Override
    public UserModel getUserByFederatedIdentity(FederatedIdentityModel socialLink, RealmModel realm) {
    	B2FederatedIdentityEntity federatedIdentityEntity = getFederatedIdentityStore().findByRealmIdAndUserIdAndIdp(realm.getId(), socialLink.getUserId(), socialLink.getIdentityProvider());
    	if(federatedIdentityEntity==null) return null;
    	B2UserEntity userEntity = getUserStore().load(federatedIdentityEntity.getUserId());
        return userEntity == null ? null : new UserAdapter(session, realm, userEntity, getUserStore());
    }

    @Override
    public UserModel getUserByServiceAccountClient(ClientModel client) {
    	B2UserEntity userEntity = getUserStore().findByRealmIdAndServiceAccountClientLink(client.getRealm().getId(), client.getId());
        return userEntity == null ? null : new UserAdapter(session, client.getRealm(), userEntity, getUserStore());
    }

    protected List<UserModel> convertUserEntities(RealmModel realm, List<B2UserEntity> userEntities) {
        List<UserModel> userModels = new ArrayList<UserModel>();
        for (B2UserEntity user : userEntities) {
            userModels.add(new UserAdapter(session, realm, user, getUserStore()));
        }
        return userModels;
    }


    @Override
    public List<UserModel> getUsers(RealmModel realm, boolean includeServiceAccounts) {
        return getUsers(realm, -1, -1, includeServiceAccounts);
    }

    @Override
    public int getUsersCount(RealmModel realm) {
    	return getUserStore().countByRealmId(realm.getId()).intValue();
    }

    @Override
    public List<UserModel> getUsers(RealmModel realm, int firstResult, int maxResults, boolean includeServiceAccounts) {
    	if(firstResult<0) firstResult=0;
    	if(maxResults<0){
    		Long count = getUserStore().countByRealmIdIncludeServiceAccount(realm.getId(), includeServiceAccounts);
    		maxResults = count.intValue();
    	}
    	List<B2UserEntity> users = getUserStore().findByRealmIdIncludeServiceAccountOrderByUsername(realm.getId(), includeServiceAccounts, firstResult, maxResults);
        return convertUserEntities(realm, users);
    }

    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm) {
        return searchForUser(search, realm, -1, -1);
    }

    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm, int firstResult, int maxResults) {
    	if(search==null) return Collections.emptyList();
    	if(firstResult<0) firstResult=0;
    	if(maxResults<0)maxResults = getUserStore().countByRealmId(realm.getId()).intValue();

    	search = search.trim();
    	List<B2UserEntity> users = getUserStore().searchUsers(realm.getId(), search, firstResult, maxResults);
    	return convertUserEntities(realm, users);
    }

    @Override
    public List<UserModel> searchForUserByAttributes(Map<String, String> attributes, RealmModel realm) {
        return searchForUserByAttributes(attributes, realm, -1, -1);
    }

    @Override
    public List<UserModel> searchForUserByAttributes(Map<String, String> attributes, RealmModel realm, int firstResult, int maxResults) {
    	if(attributes.isEmpty()) return Collections.emptyList();
    	if(firstResult<0) firstResult=0;
    	if(maxResults<0)maxResults = getUserStore().countByRealmId(realm.getId()).intValue();
    	List<B2UserEntity> users = getUserStore().searchUsers(realm.getId(), 
    		attributes.get(UserModel.USERNAME),
    		attributes.get(UserModel.FIRST_NAME),
    		attributes.get(UserModel.LAST_NAME),
    		attributes.get(UserModel.EMAIL),
    		firstResult, maxResults);
    	return convertUserEntities(realm, users);
    }

    @Override
    public List<UserModel> searchForUserByUserAttribute(String attrName, String attrValue, RealmModel realm) {
    	Long count = getUserAttributeStore().countByRealmIdAndNameAndValue(realm.getId(), attrName, attrValue);
    	if(count<1) return Collections.emptyList();
    	List<String> userIds = getUserAttributeStore().findUserIdByRealmIdAndNameAndValue(realm.getId(), attrName, attrValue, 0, count.intValue());
    	List<B2UserEntity> users = getUserStore().load(userIds);
        return convertUserEntities(realm, users);
    }

    @Override
    public Set<FederatedIdentityModel> getFederatedIdentities(UserModel userModel, RealmModel realm) {
        UserAdapter user = getUserById(userModel.getId(), realm);
        B2UserEntity userEntity = user.getUser();
        List<FederatedIdentityEntity> linkEntities = userEntity.getFederatedIdentities();

        if (linkEntities == null) {
            return Collections.emptySet();
        }

        Set<FederatedIdentityModel> result = new HashSet<FederatedIdentityModel>();
        for (FederatedIdentityEntity federatedIdentityEntity : linkEntities) {
            FederatedIdentityModel model = new FederatedIdentityModel(federatedIdentityEntity.getIdentityProvider(),
                    federatedIdentityEntity.getUserId(), federatedIdentityEntity.getUserName(), federatedIdentityEntity.getToken());
            result.add(model);
        }
        return result;
    }

    @Override
    public FederatedIdentityModel getFederatedIdentity(UserModel user, String socialProvider, RealmModel realm) {
        FederatedIdentityEntity federatedIdentityEntity = findFederatedIdentityLink(realm.getId(), user.getId(), socialProvider);

        return federatedIdentityEntity != null ? new FederatedIdentityModel(federatedIdentityEntity.getIdentityProvider(), federatedIdentityEntity.getUserId(),
                federatedIdentityEntity.getUserName(), federatedIdentityEntity.getToken()) : null;
    }

    @Override
    public UserAdapter addUser(RealmModel realm, String id, String username, boolean addDefaultRoles, boolean addDefaultRequiredActions) {
        UserAdapter userModel = addUserEntity(realm, id, username.toLowerCase());

        if (addDefaultRoles) {
            for (String r : realm.getDefaultRoles()) {
            	RoleModel role = realm.getRole(r);
            	if(role==null){
            		role = getNativeRealmProvider().getRealm(realm.getId()).getRole(r);
            	}
            	if(role==null){
            		throw new IllegalStateException("Default realm role : can not be loaded.");
            	}
                userModel.grantRole(role);
            }

            for (ClientModel application : realm.getClients()) {
                for (String r : application.getDefaultRoles()) {
                	RoleModel role = application.getRole(r);
                	if(role==null){
                		throw new IllegalStateException("Default client role : can not be loaded.");
                	}
                    userModel.grantRole(role);
                }
            }
            for (GroupModel g : realm.getDefaultGroups()) {
                userModel.joinGroup(g);
            }
        }

        if (addDefaultRequiredActions) {
            for (RequiredActionProviderModel r : realm.getRequiredActionProviders()) {
                if (r.isEnabled() && r.isDefaultAction()) {
                    userModel.addRequiredAction(r.getAlias());
                }
            }
        }


        return userModel;
    }

    protected UserAdapter addUserEntity(RealmModel realm, String id, String username) {
        B2UserEntity userEntity = new B2UserEntity();
        if(id==null)id=KeycloakModelUtils.generateId();
        userEntity.setId(id);
        userEntity.setUsername(username);
        userEntity.setCreatedTimestamp(System.currentTimeMillis());
        // Compatibility with JPA model, which has user disabled by default
        // userEntity.setEnabled(true);
        userEntity.setRealmId(realm.getId());
        getUserStore().save(userEntity);
        return new UserAdapter(session, realm, userEntity, getUserStore());
    }

    @Override
    public boolean removeUser(RealmModel realm, UserModel user) {
    	return getUserStore().remove(user.getId());
    }


    @Override
    public void addFederatedIdentity(RealmModel realm, UserModel user, FederatedIdentityModel identity) {
        B2FederatedIdentityEntity federatedIdentityEntity = new B2FederatedIdentityEntity();
        federatedIdentityEntity.setIdentityProvider(identity.getIdentityProvider());
        federatedIdentityEntity.setRealmId(realm.getId());
        federatedIdentityEntity.setUserId(user.getId());
        federatedIdentityEntity.setUserName(identity.getUserName()==null?null:identity.getUserName().toLowerCase());
        federatedIdentityEntity.setToken(identity.getToken());
        getFederatedIdentityStore().save(federatedIdentityEntity);
    }

    @Override
    public void updateFederatedIdentity(RealmModel realm, UserModel federatedUser, FederatedIdentityModel federatedIdentityModel) {
        B2FederatedIdentityEntity federatedIdentityEntity = findFederatedIdentityLink(realm.getId(), federatedUser.getId(), federatedIdentityModel.getIdentityProvider());
        federatedIdentityEntity.setToken(federatedIdentityModel.getToken());
        getFederatedIdentityStore().save(federatedIdentityEntity);
    }

    @Override
    public boolean removeFederatedIdentity(RealmModel realm, UserModel userModel, String socialProvider) {
    	B2FederatedIdentityEntity federatedIdentityEntity = findFederatedIdentityLink(realm.getId(), userModel.getId(), socialProvider);
        if (federatedIdentityEntity == null) {
            return false;
        }
        return getFederatedIdentityStore().remove(federatedIdentityEntity.getId());
    }

    private B2FederatedIdentityEntity findFederatedIdentityLink(String realmId, String userId, String identityProvider) {
    	return getFederatedIdentityStore().findByRealmIdAndUserIdAndIdp(realmId, userId, identityProvider);
    }

    @Override
    public UserModel addUser(RealmModel realm, String username) {
        return this.addUser(realm, null, username, true, true);
    }

    @Override
    public void grantToAllUsers(RealmModel realm, RoleModel role) {
    	Long count = getUserRoleStore().countByRealmIdAndRoleIdAndUserIdIsNull(realm.getId(), role.getId());
    	if(count>0) return;
    	B2UserRoleEntity entity = new B2UserRoleEntity();
    	entity.setRealmId(realm.getId());
    	entity.setRoleId(role.getId());
    	getUserRoleStore().save(entity);
    }

    @Override
    public void preRemove(RealmModel realm) {
    	// TODO: create remove model for users of this realm.
    	userStore.preRemoveByRelamId(realm.getId());
    }

    @Override
    public void preRemove(RealmModel realm, UserFederationProviderModel link) {
        // Remove all users linked with federationProvider and their consents
    	B2UserEntity entity = getUserStore().findByRealmIdAndServiceAccountClientLink(realm.getId(), link.getId());
    	if(entity!=null) getUserStore().remove(entity.getId());
    }

    @Override
    public void preRemove(RealmModel realm, ClientModel client) {
        // Remove all role mappings and consents mapped to all roles of this client
        for (RoleModel role : client.getRoles()) {
            preRemove(realm, role);
        }
        Long clientCount = getUserConsentStore().countByClientId(client.getId());
        List<B2UserConsentEntity> consenses = getUserConsentStore().findByClientId(client.getId(), 0, clientCount.intValue());
        for (B2UserConsentEntity consentEntity : consenses) {
        	getUserConsentStore().remove(consentEntity.getId());
		}
    }

    @Override
    public void preRemove(RealmModel realm, GroupModel group) {
        // Remove this role from all users, which has it
    	Long count = getGroupMemberStore().countByRealmIdAndGroupId(realm.getId(), group.getId());
    	if(count<1) return;
    	List<B2GroupMemberEntity> entities = getGroupMemberStore().findByRealmIdAndGroupId(realm.getId(), group.getId(), 0, count.intValue());
    	for (B2GroupMemberEntity entity : entities) {
    		getGroupMemberStore().remove(entity.getId());
		}
    }

    @Override
    public void preRemove(RealmModel realm, RoleModel role) {
        // Remove this role from all users, which has it
    	Long userRolesCount = getUserRoleStore().countByRealmIdAndRoleId(realm.getId(), role.getId());
    	if(userRolesCount>0){
    		List<B2UserRoleEntity> userRoles = getUserRoleStore().findByRealmIdAndRoleId(realm.getId(), role.getId(), 0, userRolesCount.intValue());
    		for (B2UserRoleEntity userRole : userRoles) {
				getUserRoleStore().remove(userRole.getId());
			}
    	}
    	
        // Remove this role from all consents, which has it
    	Long consentRoleCount = getUserConsentGrantedRoleStore().countByRealmIdAndRoleId(realm.getId(), role.getId());
    	if(consentRoleCount>0){
    		List<B2UserConsentGrantedRole> consentRoles = getUserConsentGrantedRoleStore().findByRealmIdAndRoleId(realm.getId(), role.getId(),0,consentRoleCount.intValue());
    		for (B2UserConsentGrantedRole consentRole : consentRoles) {
    			getUserConsentGrantedRoleStore().remove(consentRole.getId());
			}
    	}
    }

	public void removeStrict(String entType, String id) {
		if(B2UserEntity.ENT_TYPE.equals(entType)) {
			userStore.removeStrict(id);
		}
		if(B2UserConsentEntity.ENT_TYPE.equals(entType)) {
			userConsentStore.removeStrict(id);
		}
	}
	public UserConsentStore getUserConsentStore() {
		return userConsentStore;
	}
	public GroupMemberStore getGroupMemberStore(){
		return groupMemberStore;
	}
	public FederatedIdentityStore getFederatedIdentityStore(){
		return federatedIdentityStore;
	}

	public UserAttributeStore getUserAttributeStore() {
		return userAttributeStore;
	}
	public UserRoleStore getUserRoleStore() {
		return userRoleStore;
	}

	public UserConsentGrantedProtMapperStore getUserConsentGrantedProtMapperStore() {
		return userConsentGrantedProtMapperStore;
	}

	public UserConsentGrantedRoleStore getUserConsentGrantedRoleStore() {
		return userConsentGrantedRoleStore;
	}
	
    public UserRequiredActionStore getUserRequiredActionStore() {
		return userRequiredActionStore;
	}

	public UserStore getUserStore(){
    	return userStore;
    }

	public UserCredentialStore getUserCredentialStore() {
		return userCredentialStore;
	}

    @Override
    public void preRemove(ProtocolMapperModel protocolMapper) {
    	Long count = getUserConsentGrantedProtMapperStore().countByProtocolMapperId(protocolMapper.getId());
    	if(count==null) return;
        // Remove this protocol mapper from all consents, which has it
    	List<B2UserConsentGrantedProtMapper> mappings = getUserConsentGrantedProtMapperStore().findByByProtocolMapperId(protocolMapper.getId(), 0, count.intValue());
    	for (B2UserConsentGrantedProtMapper b2UserConsentGrantedProtMapper : mappings) {
    		getUserConsentGrantedProtMapperStore().remove(b2UserConsentGrantedProtMapper.getId());
		}
    }

    @Override
    public boolean validCredentials(KeycloakSession session, RealmModel realm, UserModel user, List<UserCredentialModel> input) {
        return CredentialValidation.validCredentials(session, realm, user, input);
    }

    @Override
    public boolean validCredentials(KeycloakSession session, RealmModel realm, UserModel user, UserCredentialModel... input) {
        return CredentialValidation.validCredentials(session, realm, user, input);
    }

    @Override
    public CredentialValidationOutput validCredentials(KeycloakSession session, RealmModel realm, UserCredentialModel... input) {
        // Not supported yet
        return null;
    }

}