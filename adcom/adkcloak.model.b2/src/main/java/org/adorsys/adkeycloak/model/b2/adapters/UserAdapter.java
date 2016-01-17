package org.adorsys.adkeycloak.model.b2.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adorsys.adkeycloak.model.b2.jpa.B2GroupMemberEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserAttributeEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserConsentEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserCredentialEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserRequiredActionEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserRoleEntity;
import org.adorsys.adkeycloak.model.b2.repo.UserStore;
import org.keycloak.common.util.Time;
import org.keycloak.hash.PasswordHashManager;
import org.keycloak.models.ClientModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ModelDuplicateException;
import org.keycloak.models.ModelException;
import org.keycloak.models.OTPPolicy;
import org.keycloak.models.PasswordPolicy;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserConsentModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserCredentialValueModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.entities.CredentialEntity;
import org.keycloak.models.entities.UserConsentEntity;
import org.keycloak.models.utils.KeycloakModelUtils;

public class UserAdapter extends B2AbstractUserAdapter<B2UserEntity> implements UserModel {
    
    private final B2UserEntity user;
    private final RealmModel realm;
    private final KeycloakSession session;

    public UserAdapter(KeycloakSession session, RealmModel realm, B2UserEntity userEntity, UserStore store) {
        super(store);
        this.user = userEntity;
        this.realm = realm;
        this.session=session;
    }

    @Override
    public String getId() {
        return user.getId();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public void setUsername(String username) {
        username = KeycloakModelUtils.toLowerCaseSafe(username);

        user.setUsername(username);
        updateUser();
    }

    @Override
    public Long getCreatedTimestamp() {
        return user.getCreatedTimestamp();
    }

    @Override
    public void setCreatedTimestamp(Long timestamp) {
        user.setCreatedTimestamp(timestamp);
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        user.setEnabled(enabled);
        updateUser();
    }

    @Override
    public String getFirstName() {
        return user.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        user.setFirstName(firstName);
        updateUser();
    }

    @Override
    public String getLastName() {
        return user.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        user.setLastName(lastName);
        updateUser();
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public void setEmail(String email) {
        email = KeycloakModelUtils.toLowerCaseSafe(email);

        user.setEmail(email);
        updateUser();
    }

    @Override
    public boolean isEmailVerified() {
        return user.isEmailVerified();
    }

    @Override
    public void setEmailVerified(boolean verified) {
        user.setEmailVerified(verified);
        updateUser();
    }

    @Override
    public void setSingleAttribute(String name, String value) {
    	Long count = getUserAttributeStore().countByRealmIdAndUserIdAndName(user.getRealmId(), user.getId(), name);
    	if(count>0) {// attribute exists. Delete an replace with single value.
    		List<B2UserAttributeEntity> list = getUserAttributeStore().findByRealmIdAndUserIdAndName(user.getRealmId(), user.getId(), name, 0, count.intValue());
    		B2UserAttributeEntity userAttributeEntity = null;
    		for (B2UserAttributeEntity ua : list) {
				if(userAttributeEntity==null){ // retain the first object.
					userAttributeEntity = ua;
				} else { // delete all others.
					getUserAttributeStore().remove(ua.getId());
				}
			}
    		if(userAttributeEntity!=null){
	    		userAttributeEntity.setValue(value);
	        	getUserAttributeStore().save(userAttributeEntity);
    		}
    	} else {
        	B2UserAttributeEntity userAttributeEntity = new B2UserAttributeEntity();
        	userAttributeEntity.setName(name);
        	userAttributeEntity.setRealmId(user.getRealmId());
        	userAttributeEntity.setUserId(user.getId());
        	userAttributeEntity.setValue(value);
        	userAttributeEntity.setId(KeycloakModelUtils.generateId());
        	getUserAttributeStore().save(userAttributeEntity);
    	}
    }

    @Override
    public void setAttribute(String name, List<String> values) {
    	Long count = getUserAttributeStore().countByRealmIdAndUserIdAndName(user.getRealmId(), user.getId(), name);
    	List<String> toCreate = new ArrayList<String>();
    	if(count>0) { 
    		List<B2UserAttributeEntity> list = getUserAttributeStore().findByRealmIdAndUserIdAndName(user.getRealmId(), user.getId(), name, 0, count.intValue());
    		for (B2UserAttributeEntity ua : list) {
				if(values.contains(ua.getValue())){
					toCreate.remove(ua.getValue());
				} else {
					// remove
					getUserAttributeStore().remove(ua.getId());
				}
			}
    	}
    	for (String value : toCreate) {
        	B2UserAttributeEntity userAttributeEntity = new B2UserAttributeEntity();
        	userAttributeEntity.setName(name);
        	userAttributeEntity.setRealmId(user.getRealmId());
        	userAttributeEntity.setUserId(user.getId());
        	userAttributeEntity.setValue(value);
        	userAttributeEntity.setId(KeycloakModelUtils.generateId());
        	getUserAttributeStore().save(userAttributeEntity);
		}
    }

    @Override
    public void removeAttribute(String name) {
		List<B2UserAttributeEntity> list = getUserAttributeStore().findByRealmIdAndUserIdAndName(user.getRealmId(), user.getId(), name, -1,-1);
		for (B2UserAttributeEntity ua : list) {
			getUserAttributeStore().remove(ua.getId());
		}
    }

    @Override
    public String getFirstAttribute(String name) {
    	Long count = getUserAttributeStore().countByRealmIdAndUserIdAndName(user.getRealmId(), user.getId(), name);
    	if(count<1) return null;
    	
    	List<B2UserAttributeEntity> list = getUserAttributeStore().findByRealmIdAndUserIdAndName(user.getRealmId(), user.getId(), name, 0,1);
    	if(list.isEmpty()) return null;
    	return list.iterator().next().getValue();
    }

    @Override
    public List<String> getAttribute(String name) {
    	Long count = getUserAttributeStore().countByRealmIdAndUserIdAndName(user.getRealmId(), user.getId(), name);
    	if(count<1) return Collections.emptyList();
    	
    	return getUserAttributeStore().loadValueByRealmIdAndUserIdAndName(user.getRealmId(), user.getId(), name, 0,count.intValue());
    }

    @Override
    public Map<String, List<String>> getAttributes() {
    	Long count = getUserAttributeStore().countByRealmIdAndUserId(user.getRealmId(), user.getId());
    	if(count<1) return Collections.<String, List<String>>emptyMap();
    	Map<String, List<String>> result = new HashMap<String, List<String>>();
    	List<B2UserAttributeEntity> list = getUserAttributeStore().findByRealmIdAndUserId(user.getRealmId(), user.getId(), 0,count.intValue());
    	for (B2UserAttributeEntity ua : list) {
			if(!result.containsKey(ua.getName())){
				result.put(ua.getName(), new ArrayList<String>());
			}
			result.get(ua.getName()).add(ua.getValue());
		}
		return result;
    }

    public B2UserEntity getUser() {
        return user;
    }

    @Override
    public Set<String> getRequiredActions() {
    	Long count = getUserRequiredActionStore().countByRealmIdAndUserId(user.getRealmId(), user.getId());
    	if(count<1) return Collections.<String>emptySet();
    	List<String> entities = getUserRequiredActionStore().findNameByRealmIdAndUserId(user.getRealmId(), user.getId(), 0, count.intValue());
    	return new HashSet<String>(entities);
    }

    @Override
    public void addRequiredAction(RequiredAction action) {
    	if(action==null) return;
        addRequiredAction(action.name());
    }

    @Override
    public void addRequiredAction(String actionName) {
    	if(actionName==null) return;
    	Long count = getUserRequiredActionStore().countByRealmIdAndUserIdAndName(user.getRealmId(), user.getId(), actionName);
    	if(count>0) return;

    	B2UserRequiredActionEntity entity = new B2UserRequiredActionEntity();
    	entity.setName(actionName);
    	entity.setRealmId(user.getRealmId());
    	entity.setUserId(user.getId());
    	entity.setId(KeycloakModelUtils.generateId());
    	getUserRequiredActionStore().save(entity);
    }

    @Override
    public void removeRequiredAction(RequiredAction action) {
    	if(action==null) return;
        String actionName = action.name();
        removeRequiredAction(actionName);
    }

    @Override
    public void removeRequiredAction(String actionName) {
    	if(actionName==null) return;
    	Long count = getUserRequiredActionStore().countByRealmIdAndUserIdAndName(user.getRealmId(), user.getId(), actionName);
    	if(count<1) return;
    	List<B2UserRequiredActionEntity> entities = getUserRequiredActionStore().findByRealmIdAndUserIdAndName(user.getRealmId(), user.getId(), actionName, 0, count.intValue());
    	for (B2UserRequiredActionEntity b2UserRequiredActionEntity : entities) {
    		getUserRequiredActionStore().remove(b2UserRequiredActionEntity.getId());
		}
    }

    @Override
    public boolean isOtpEnabled() {
        return user.isTotp();
    }

    @Override
    public void setOtpEnabled(boolean totp) {
        user.setTotp(totp);
        updateUser();
    }

    @Override
    public void updateCredential(UserCredentialModel cred) {

        if (cred.getType().equals(UserCredentialModel.PASSWORD)) {
            updatePasswordCredential(cred);
        } else if (UserCredentialModel.isOtp(cred.getType())){
            updateOtpCredential(cred);
        } else {
            B2UserCredentialEntity credentialEntity = getCredentialEntity(user, cred.getType());

            if (credentialEntity == null) {
                credentialEntity = setCredentials(user, cred);
                credentialEntity.setValue(cred.getValue());
                credentialEntity.setId(KeycloakModelUtils.generateId());
                getUserCredentialStore().save(credentialEntity);
            } else {
                credentialEntity.setValue(cred.getValue());
                getUserCredentialStore().save(credentialEntity);
            }
        }
    }

    private void updateOtpCredential(UserCredentialModel cred) {
    	B2UserCredentialEntity credentialEntity = getCredentialEntity(user, cred.getType());

        if (credentialEntity == null) {
            credentialEntity = setCredentials(user, cred);
            credentialEntity.setValue(cred.getValue());
            OTPPolicy otpPolicy = realm.getOTPPolicy();
            credentialEntity.setAlgorithm(otpPolicy.getAlgorithm());
            credentialEntity.setDigits(otpPolicy.getDigits());
            credentialEntity.setCounter(otpPolicy.getInitialCounter());
            credentialEntity.setPeriod(otpPolicy.getPeriod());
        	credentialEntity.setId(KeycloakModelUtils.generateId());
        } else {
            credentialEntity.setValue(cred.getValue());
            OTPPolicy policy = realm.getOTPPolicy();
            credentialEntity.setDigits(policy.getDigits());
            credentialEntity.setCounter(policy.getInitialCounter());
            credentialEntity.setAlgorithm(policy.getAlgorithm());
            credentialEntity.setPeriod(policy.getPeriod());
        }
        getUserCredentialStore().save(credentialEntity);
    }


    private void updatePasswordCredential(UserCredentialModel cred) {
    	B2UserCredentialEntity credentialEntity = getCredentialEntity(user, cred.getType());

        if (credentialEntity == null) {
            credentialEntity = setCredentials(user, cred);
            setValue(credentialEntity, cred);
            credentialEntity.setId(KeycloakModelUtils.generateId());
            getUserCredentialStore().save(credentialEntity);
        } else {

            int expiredPasswordsPolicyValue = -1;
            PasswordPolicy policy = realm.getPasswordPolicy();
            if(policy != null) {
                expiredPasswordsPolicyValue = policy.getExpiredPasswords();
            }
            
            if (expiredPasswordsPolicyValue != -1) {
                credentialEntity.setType(UserCredentialModel.PASSWORD_HISTORY);
                getUserCredentialStore().save(credentialEntity);

                List<B2UserCredentialEntity> credentialEntities = getCredentialEntities(user, UserCredentialModel.PASSWORD_HISTORY);
                if (credentialEntities.size() > expiredPasswordsPolicyValue - 1) {
                    List<B2UserCredentialEntity> toRemove = credentialEntities.subList(expiredPasswordsPolicyValue - 1, credentialEntities.size());
                    for (B2UserCredentialEntity credential2Remove : toRemove) {
                    	getUserCredentialStore().remove(credential2Remove.getId());
					}
                }

                credentialEntity = setCredentials(user, cred);
                setValue(credentialEntity, cred);
            	credentialEntity.setId(KeycloakModelUtils.generateId());
                getUserCredentialStore().save(credentialEntity);
            } else {
                List<B2UserCredentialEntity> credentialEntities = getCredentialEntities(user, UserCredentialModel.PASSWORD_HISTORY);
                if (credentialEntities != null && credentialEntities.size() > 0) {
                    for (B2UserCredentialEntity credential2Remove : credentialEntities) {
                    	getUserCredentialStore().remove(credential2Remove.getId());
					}
                }
                setValue(credentialEntity, cred);
                getUserCredentialStore().save(credentialEntity);
            }
        }
    }
    
    private B2UserCredentialEntity setCredentials(B2UserEntity user, UserCredentialModel cred) {
    	B2UserCredentialEntity credentialEntity = new B2UserCredentialEntity();
        credentialEntity.setType(cred.getType());
        credentialEntity.setDevice(cred.getDevice());
        credentialEntity.setUserId(user.getId());
        credentialEntity.setRealmId(user.getRealmId());
        return credentialEntity;
    }

    private void setValue(B2UserCredentialEntity credentialEntity, UserCredentialModel cred) {
        UserCredentialValueModel encoded = PasswordHashManager.encode(session, realm, cred.getValue());
        credentialEntity.setCreatedDate(Time.toMillis(Time.currentTime()));
        credentialEntity.setAlgorithm(encoded.getAlgorithm());
        credentialEntity.setValue(encoded.getValue());
        credentialEntity.setSalt(encoded.getSalt());
        credentialEntity.setHashIterations(encoded.getHashIterations());
    }

    private B2UserCredentialEntity getCredentialEntity(B2UserEntity userEntity, String credType) {
    	List<B2UserCredentialEntity> entities = getUserCredentialStore().findByRealmIdAndUserIdAndType(userEntity.getRealmId(), userEntity.getId(), credType, 0, 1);
    	if(entities.isEmpty()) return null;
    	return entities.iterator().next();
    }

    private List<B2UserCredentialEntity> getCredentialEntities(B2UserEntity userEntity, String credType) {
    	Long count = getUserCredentialStore().countByRealmIdAndUserIdAndType(userEntity.getRealmId(), userEntity.getId(), credType);
    	return getUserCredentialStore().findByRealmIdAndUserIdAndType(userEntity.getRealmId(), userEntity.getId(), credType, 0, count.intValue());
    }

    @Override
    public List<UserCredentialValueModel> getCredentialsDirectly() {
    	Long count = getUserCredentialStore().countByRealmIdAndUserId(user.getRealmId(), user.getId());
    	List<B2UserCredentialEntity> credentials = getUserCredentialStore().findByRealmIdAndUserId(user.getRealmId(), user.getId(), 0, count.intValue());
        List<UserCredentialValueModel> result = new ArrayList<UserCredentialValueModel>();
        for (CredentialEntity credEntity : credentials) {
            UserCredentialValueModel credModel = new UserCredentialValueModel();
            credModel.setType(credEntity.getType());
            credModel.setDevice(credEntity.getDevice());
            credModel.setCreatedDate(credEntity.getCreatedDate());
            credModel.setValue(credEntity.getValue());
            credModel.setSalt(credEntity.getSalt());
            credModel.setHashIterations(credEntity.getHashIterations());
            if (UserCredentialModel.isOtp(credEntity.getType())) {
                credModel.setCounter(credEntity.getCounter());
                if (credEntity.getAlgorithm() == null) {
                    // for migration where these values would be null
                    credModel.setAlgorithm(realm.getOTPPolicy().getAlgorithm());
                } else {
                    credModel.setAlgorithm(credEntity.getAlgorithm());
                }
                if (credEntity.getDigits() == 0) {
                    // for migration where these values would be 0
                    credModel.setDigits(realm.getOTPPolicy().getDigits());
                } else {
                    credModel.setDigits(credEntity.getDigits());
                }

                if (credEntity.getPeriod() == 0) {
                    // for migration where these values would be 0
                    credModel.setPeriod(realm.getOTPPolicy().getPeriod());
                } else {
                    credModel.setPeriod(credEntity.getPeriod());
                }
            }

            result.add(credModel);
        }

        return result;
    }

    @Override
    public void updateCredentialDirectly(UserCredentialValueModel credModel) {
        B2UserCredentialEntity credentialEntity = getCredentialEntity(user, credModel.getType());

        if (credentialEntity == null) {
            credentialEntity = new B2UserCredentialEntity();
            credentialEntity.setRealmId(user.getRealmId());
            credentialEntity.setUserId(user.getId());
            credentialEntity.setType(credModel.getType());
            credentialEntity.setCreatedDate(credModel.getCreatedDate());
        	credentialEntity.setId(KeycloakModelUtils.generateId());

        }

        credentialEntity.setValue(credModel.getValue());
        credentialEntity.setSalt(credModel.getSalt());
        credentialEntity.setDevice(credModel.getDevice());
        credentialEntity.setHashIterations(credModel.getHashIterations());
        credentialEntity.setCounter(credModel.getCounter());
        credentialEntity.setAlgorithm(credModel.getAlgorithm());
        credentialEntity.setDigits(credModel.getDigits());
        credentialEntity.setPeriod(credModel.getPeriod());

        getUserCredentialStore().save(credentialEntity);
    }

    protected void updateUser() {
        super.updateEntity();
    }

    @Override
    public B2UserEntity getEntity() {
        return user;
    }

    @Override
    public Set<GroupModel> getGroups() {
    	Long count = getGroupMemberStore().countByRealmIdAndUserId(realm.getId(), user.getId());
    	if(count<1) return Collections.<GroupModel>emptySet();
    	
    	Set<GroupModel> groups = new HashSet<GroupModel>(count.intValue());
    	List<B2GroupMemberEntity> entities = getGroupMemberStore().findByRealmIdAndUserId(realm.getId(), user.getId(), 0, count.intValue());
    	for (B2GroupMemberEntity entity : entities) {
    		groups.add(realm.getGroupById(entity.getId()));
		}
        return groups;
    }

    @Override
    public void joinGroup(GroupModel group) {
    	Long count = getGroupMemberStore().countByRealmIdAndUserIdAndroupId(realm.getId(), user.getId(), group.getId());
    	if(count>0) return;
    	B2GroupMemberEntity entity = new B2GroupMemberEntity();
    	entity.setGroupId(group.getId());
    	entity.setRealmId(realm.getId());
    	entity.setUserId(user.getId());
    	entity.setUsername(user.getUsername());
    	entity.setId(KeycloakModelUtils.generateId());
    	getGroupMemberStore().save(entity);
    }

    @Override
    public void leaveGroup(GroupModel group) {
    	Long count = getGroupMemberStore().countByRealmIdAndUserIdAndroupId(realm.getId(), user.getId(), group.getId());
    	if(count>0) return;
    	List<B2GroupMemberEntity> entities = getGroupMemberStore().findByRealmIdAndUserIdAndGroupId(realm.getId(), user.getId(), group.getId(), 0, count.intValue());
    	for (B2GroupMemberEntity entity : entities) {
    		getGroupMemberStore().remove(entity.getId());
		}
    }

    @Override
    public boolean isMemberOf(GroupModel group) {
    	Long count = getGroupMemberStore().countByRealmIdAndUserIdAndroupId(realm.getId(), user.getId(), group.getId());
    	if(count>0) return true;

        Set<GroupModel> groups = getGroups();
        return KeycloakModelUtils.isMember(groups, group);
    }

    @Override
    public boolean hasRole(RoleModel role) {
    	Long count = getUserRoleSttore().countByRealmIdAndUserIdAndRoleId(realm.getId(), user.getId(), role.getId());
    	if(count>0) return true;
    	
        Set<RoleModel> roles = getRoleMappings();
        return KeycloakModelUtils.hasRole(roles, role);
    }

    @Override
    public void grantRole(RoleModel role) {
    	Long count = getUserRoleSttore().countByRealmIdAndUserIdAndRoleId(realm.getId(), user.getId(), role.getId());
    	if(count>0) return;
    	
    	count = getUserRoleSttore().countByRealmIdAndRoleIdAndUserIdIsNull(realm.getId(), user.getId());
    	if(count>0) return;

    	B2UserRoleEntity entity = new B2UserRoleEntity();
    	entity.setId(KeycloakModelUtils.generateId());
    	entity.setRealmId(realm.getId());
    	entity.setRoleId(role.getId());
    	entity.setUserId(user.getId());
    	getUserRoleSttore().save(entity);
    }

    @Override
    public Set<RoleModel> getRoleMappings() {
        List<RoleModel> roles = getAllRolesOfUser(realm, this);
        return new HashSet<RoleModel>(roles);
    }

    @Override
    public Set<RoleModel> getRealmRoleMappings() {
        Set<RoleModel> allRoles = getRoleMappings();

        // Filter to retrieve just realm roles
        Set<RoleModel> realmRoles = new HashSet<RoleModel>();
        for (RoleModel role : allRoles) {
            if (role.getContainer() instanceof RealmModel) {
                realmRoles.add(role);
            }
        }
        return realmRoles;
    }

    @Override
    public void deleteRoleMapping(RoleModel role) {
        if (user == null || role == null) return;
    	Long count = getUserRoleSttore().countByRealmIdAndUserIdAndRoleId(realm.getId(), user.getId(), role.getId());
    	if(count<1) return;

    	List<B2UserRoleEntity> entities = getUserRoleSttore().findByRealmIdAndUserIdAndRoleId(realm.getId(), user.getId(), role.getId(), 0, count.intValue());
    	for (B2UserRoleEntity entity : entities) {
    		getUserRoleSttore().remove(entity.getId());
		}
    }

    @Override
    public Set<RoleModel> getClientRoleMappings(ClientModel app) {
        Set<RoleModel> result = new HashSet<RoleModel>();
        List<RoleModel> roles = getAllRolesOfUser(realm, this);

        for (RoleModel role : roles) {
            if (app.equals(role.getContainer())) {
                result.add(role);
            }
        }
        return result;
    }

    @Override
    public String getFederationLink() {
        return user.getFederationLink();
    }

    @Override
    public void setFederationLink(String link) {
        user.setFederationLink(link);
        updateUser();
    }

    @Override
    public String getServiceAccountClientLink() {
        return user.getServiceAccountClientLink();
    }

    @Override
    public void setServiceAccountClientLink(String clientInternalId) {
        user.setServiceAccountClientLink(clientInternalId);
        updateUser();
    }

    @Override
    public void addConsent(UserConsentModel consent) {
        String clientId = consent.getClient().getId();
        if (getConsentEntityByClientId(clientId) != null) {
            throw new ModelDuplicateException("Consent already exists for client [" + clientId + "] and user [" + user.getId() + "]");
        }

        B2UserConsentEntity consentEntity = new B2UserConsentEntity();
        consentEntity.setUserId(getId());
        consentEntity.setClientId(clientId);
        fillEntityFromModel(consent, consentEntity);
    	consentEntity.setId(KeycloakModelUtils.generateId());
        getUserConsentStore().save(consentEntity);
    }

    @Override
    public UserConsentModel getConsentByClient(String clientId) {
        UserConsentEntity consentEntity = getConsentEntityByClientId(clientId);
        return consentEntity!=null ? toConsentModel(consentEntity) : null;
    }

    @Override
    public List<UserConsentModel> getConsents() {
        List<UserConsentModel> result = new ArrayList<UserConsentModel>();

        Long count = getUserConsentStore().countByUserId(user.getId());
        List<B2UserConsentEntity> grantedConsents = getUserConsentStore().findByUserId(user.getId(), 0, count.intValue());

        for (UserConsentEntity consentEntity : grantedConsents) {
            UserConsentModel model = toConsentModel(consentEntity);
            result.add(model);
        }

        return result;
    }

    private B2UserConsentEntity getConsentEntityByClientId(String clientId) {
        List<B2UserConsentEntity> entities = getUserConsentStore().findByUserIdAndClientId(getId(), clientId, 0, 1);
        if(entities.isEmpty()) return null;
        return entities.iterator().next();
    }

    private UserConsentModel toConsentModel(UserConsentEntity entity) {
        ClientModel client = realm.getClientById(entity.getClientId());
        if (client == null) {
            throw new ModelException("Client with id " + entity.getClientId() + " is not available");
        }
        UserConsentModel model = new UserConsentModel(client);

        for (String roleId : entity.getGrantedRoles()) {
            RoleModel roleModel = realm.getRoleById(roleId);
            if (roleModel != null) {
                model.addGrantedRole(roleModel);
            }
        }

        for (String protMapperId : entity.getGrantedProtocolMappers()) {
            ProtocolMapperModel protocolMapper = client.getProtocolMapperById(protMapperId);
            model.addGrantedProtocolMapper(protocolMapper);
        }
        return model;
    }

    // Fill roles and protocolMappers to entity
    private void fillEntityFromModel(UserConsentModel consent, B2UserConsentEntity consentEntity) {
        List<String> roleIds = new LinkedList<String>();
        for (RoleModel role : consent.getGrantedRoles()) {
            roleIds.add(role.getId());
        }
        consentEntity.setGrantedRoles(roleIds);

        List<String> protMapperIds = new LinkedList<String>();
        for (ProtocolMapperModel protMapperModel : consent.getGrantedProtocolMappers()) {
            protMapperIds.add(protMapperModel.getId());
        }
        consentEntity.setGrantedProtocolMappers(protMapperIds);
    }

    @Override
    public void updateConsent(UserConsentModel consent) {
        String clientId = consent.getClient().getId();
        B2UserConsentEntity consentEntity = getConsentEntityByClientId(clientId);
        if (consentEntity == null) {
            throw new ModelException("Consent not found for client [" + clientId + "] and user [" + user.getId() + "]");
        } else {
            fillEntityFromModel(consent, consentEntity);
            getUserConsentStore().save(consentEntity);
        }
    }

    @Override
    public boolean revokeConsentForClient(String clientId) {
        B2UserConsentEntity entity = getConsentEntityByClientId(clientId);
        if (entity == null) {
            return false;
        }

        return getUserConsentStore().remove(entity.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof UserModel)) return false;

        UserModel that = (UserModel) o;
        return that.getId().equals(getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    public List<RoleModel> getAllRolesOfUser(RealmModel realm, UserModel user) {
    	Long count = getUserRoleSttore().countUserRoles(realm.getId(), user.getId());
    	List<String> roleIds = getUserRoleSttore().findRoleIdsOfUserRoles(realm.getId(), user.getId(), 0, count.intValue());

        if (roleIds == null || roleIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<RoleModel> roles = new LinkedList<RoleModel>();
        for (String roleId : roleIds) {
            RoleModel role = realm.getRoleById(roleId);
            if (role != null) {
                roles.add(role);
            }
        }
        return roles;
    }
}
