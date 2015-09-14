package org.keycloak.models.jpa;

import org.keycloak.models.ClientModel;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserConsentModel;
import org.keycloak.models.ModelDuplicateException;
import org.keycloak.models.ModelException;
import org.keycloak.models.PasswordPolicy;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleContainerModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserCredentialValueModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.jpa.entities.CredentialEntity;
import org.keycloak.models.jpa.entities.UserConsentEntity;
import org.keycloak.models.jpa.entities.UserConsentProtocolMapperEntity;
import org.keycloak.models.jpa.entities.UserConsentRoleEntity;
import org.keycloak.models.jpa.entities.UserAttributeEntity;
import org.keycloak.models.jpa.entities.UserEntity;
import org.keycloak.models.jpa.entities.UserRequiredActionEntity;
import org.keycloak.models.jpa.entities.UserRoleMappingEntity;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.keycloak.models.utils.Pbkdf2PasswordEncoder;
import org.keycloak.util.MultivaluedHashMap;
import org.keycloak.util.Time;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.keycloak.models.utils.Pbkdf2PasswordEncoder.getSalt;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class UserAdapter implements UserModel {

    protected UserEntity user;
    protected EntityManager em;
    protected RealmModel realm;

    public UserAdapter(RealmModel realm, EntityManager em, UserEntity user) {
        this.em = em;
        this.user = user;
        this.realm = realm;
    }

    public UserEntity getUser() {
        return user;
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
    public boolean isTotp() {
        return user.isTotp();
    }

    @Override
    public void setEnabled(boolean enabled) {
        user.setEnabled(enabled);
    }

    @Override
    public void setSingleAttribute(String name, String value) {
        boolean found = false;
        List<UserAttributeEntity> toRemove = new ArrayList<>();
        for (UserAttributeEntity attr : user.getAttributes()) {
            if (attr.getName().equals(name)) {
                if (!found) {
                    attr.setValue(value);
                    found = true;
                } else {
                    toRemove.add(attr);
                }
            }
        }

        for (UserAttributeEntity attr : toRemove) {
            em.remove(attr);
            user.getAttributes().remove(attr);
        }

        if (found) {
            return;
        }

        persistAttributeValue(name, value);
    }

    @Override
    public void setAttribute(String name, List<String> values) {
        // Remove all existing
        removeAttribute(name);

        // Put all new
        for (String value : values) {
            persistAttributeValue(name, value);
        }
    }

    private void persistAttributeValue(String name, String value) {
        UserAttributeEntity attr = new UserAttributeEntity();
        attr.setId(KeycloakModelUtils.generateId());
        attr.setName(name);
        attr.setValue(value);
        attr.setUser(user);
        em.persist(attr);
        user.getAttributes().add(attr);
    }

    @Override
    public void removeAttribute(String name) {
        Iterator<UserAttributeEntity> it = user.getAttributes().iterator();
        while (it.hasNext()) {
            UserAttributeEntity attr = it.next();
            if (attr.getName().equals(name)) {
                it.remove();
                em.remove(attr);
            }
        }
    }

    @Override
    public String getFirstAttribute(String name) {
        for (UserAttributeEntity attr : user.getAttributes()) {
            if (attr.getName().equals(name)) {
                return attr.getValue();
            }
        }
        return null;
    }

    @Override
    public List<String> getAttribute(String name) {
        List<String> result = new ArrayList<>();
        for (UserAttributeEntity attr : user.getAttributes()) {
            if (attr.getName().equals(name)) {
                result.add(attr.getValue());
            }
        }
        return result;
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        MultivaluedHashMap<String, String> result = new MultivaluedHashMap<>();
        for (UserAttributeEntity attr : user.getAttributes()) {
            result.add(attr.getName(), attr.getValue());
        }
        return result;
    }

    @Override
    public Set<String> getRequiredActions() {
        Set<String> result = new HashSet<>();
        for (UserRequiredActionEntity attr : user.getRequiredActions()) {
            result.add(attr.getAction());
        }
        return result;
    }

    @Override
    public void addRequiredAction(RequiredAction action) {
        String actionName = action.name();
        addRequiredAction(actionName);
    }

    @Override
    public void addRequiredAction(String actionName) {
        for (UserRequiredActionEntity attr : user.getRequiredActions()) {
            if (attr.getAction().equals(actionName)) {
                return;
            }
        }
        UserRequiredActionEntity attr = new UserRequiredActionEntity();
        attr.setAction(actionName);
        attr.setUser(user);
        em.persist(attr);
        user.getRequiredActions().add(attr);
    }

    @Override
    public void removeRequiredAction(RequiredAction action) {
        String actionName = action.name();
        removeRequiredAction(actionName);
    }

    @Override
    public void removeRequiredAction(String actionName) {
        Iterator<UserRequiredActionEntity> it = user.getRequiredActions().iterator();
        while (it.hasNext()) {
            UserRequiredActionEntity attr = it.next();
            if (attr.getAction().equals(actionName)) {
                it.remove();
                em.remove(attr);
            }
        }
    }

    @Override
    public String getFirstName() {
        return user.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        user.setFirstName(firstName);
    }

    @Override
    public String getLastName() {
        return user.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        user.setLastName(lastName);
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public void setEmail(String email) {
        email = KeycloakModelUtils.toLowerCaseSafe(email);
        user.setEmail(email);
    }

    @Override
    public boolean isEmailVerified() {
        return user.isEmailVerified();
    }

    @Override
    public void setEmailVerified(boolean verified) {
        user.setEmailVerified(verified);
    }

    @Override
    public void setTotp(boolean totp) {
        user.setTotp(totp);
    }

    @Override
    public void updateCredential(UserCredentialModel cred) {

        if (cred.getType().equals(UserCredentialModel.PASSWORD)) {
            updatePasswordCredential(cred);
        } else {
            CredentialEntity credentialEntity = getCredentialEntity(user, cred.getType());

            if (credentialEntity == null) {
                credentialEntity = setCredentials(user, cred);
                credentialEntity.setValue(cred.getValue());
                em.persist(credentialEntity);
                user.getCredentials().add(credentialEntity);
            } else {
                credentialEntity.setValue(cred.getValue());
            }
        }
        em.flush();
    }

    private void updatePasswordCredential(UserCredentialModel cred) {
        CredentialEntity credentialEntity = getCredentialEntity(user, cred.getType());

        if (credentialEntity == null) {
            credentialEntity = setCredentials(user, cred);
            setValue(credentialEntity, cred);
            em.persist(credentialEntity);
            user.getCredentials().add(credentialEntity);
        } else {
            
            int expiredPasswordsPolicyValue = -1;
            PasswordPolicy policy = realm.getPasswordPolicy();
            if(policy != null) {
                expiredPasswordsPolicyValue = policy.getExpiredPasswords();
            }

            if (expiredPasswordsPolicyValue != -1) {
                user.getCredentials().remove(credentialEntity);
                credentialEntity.setType(UserCredentialModel.PASSWORD_HISTORY);
                user.getCredentials().add(credentialEntity);

                List<CredentialEntity> credentialEntities = getCredentialEntities(user, UserCredentialModel.PASSWORD_HISTORY);
                if (credentialEntities.size() > expiredPasswordsPolicyValue - 1) {
                    user.getCredentials().removeAll(credentialEntities.subList(expiredPasswordsPolicyValue - 1, credentialEntities.size()));
                }

                credentialEntity = setCredentials(user, cred);
                setValue(credentialEntity, cred);
                em.persist(credentialEntity);
                user.getCredentials().add(credentialEntity);
            } else {
                List<CredentialEntity> credentialEntities = getCredentialEntities(user, UserCredentialModel.PASSWORD_HISTORY);
                if (credentialEntities != null && credentialEntities.size() > 0) {
                    user.getCredentials().removeAll(credentialEntities);
                }
                setValue(credentialEntity, cred);
            }
        }
    }
    
    private CredentialEntity setCredentials(UserEntity user, UserCredentialModel cred) {
        CredentialEntity credentialEntity = new CredentialEntity();
        credentialEntity.setId(KeycloakModelUtils.generateId());
        credentialEntity.setType(cred.getType());
        credentialEntity.setDevice(cred.getDevice());
        credentialEntity.setUser(user);
        return credentialEntity;
    }

    private void setValue(CredentialEntity credentialEntity, UserCredentialModel cred) {
        byte[] salt = getSalt();
        int hashIterations = 1;
        PasswordPolicy policy = realm.getPasswordPolicy();
        if (policy != null) {
            hashIterations = policy.getHashIterations();
            if (hashIterations == -1)
                hashIterations = 1;
        }
        credentialEntity.setCreatedDate(Time.toMillis(Time.currentTime()));
        credentialEntity.setValue(new Pbkdf2PasswordEncoder(salt).encode(cred.getValue(), hashIterations));
        credentialEntity.setSalt(salt);
        credentialEntity.setHashIterations(hashIterations);
    }

    private CredentialEntity getCredentialEntity(UserEntity userEntity, String credType) {
        for (CredentialEntity entity : userEntity.getCredentials()) {
            if (entity.getType().equals(credType)) {
                return entity;
            }
        }

        return null;
    }

    private List<CredentialEntity> getCredentialEntities(UserEntity userEntity, String credType) {
        List<CredentialEntity> credentialEntities = new ArrayList<CredentialEntity>();
        for (CredentialEntity entity : userEntity.getCredentials()) {
            if (entity.getType().equals(credType)) {
                credentialEntities.add(entity);
            }
        }

        // Avoiding direct use of credSecond.getCreatedDate() - credFirst.getCreatedDate() to prevent Integer Overflow
        // Orders from most recent to least recent
        Collections.sort(credentialEntities, new Comparator<CredentialEntity>() {
            public int compare(CredentialEntity credFirst, CredentialEntity credSecond) {
                if (credFirst.getCreatedDate() > credSecond.getCreatedDate()) {
                    return -1;
                } else if (credFirst.getCreatedDate() < credSecond.getCreatedDate()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return credentialEntities;
    }

    @Override
    public List<UserCredentialValueModel> getCredentialsDirectly() {
        List<CredentialEntity> credentials = new ArrayList<CredentialEntity>(user.getCredentials());
        List<UserCredentialValueModel> result = new ArrayList<UserCredentialValueModel>();

        if (credentials != null) {
            for (CredentialEntity credEntity : credentials) {
                UserCredentialValueModel credModel = new UserCredentialValueModel();
                credModel.setType(credEntity.getType());
                credModel.setDevice(credEntity.getDevice());
                credModel.setValue(credEntity.getValue());
                credModel.setCreatedDate(credEntity.getCreatedDate());
                credModel.setSalt(credEntity.getSalt());
                credModel.setHashIterations(credEntity.getHashIterations());

                result.add(credModel);
            }
        }

        return result;
    }

    @Override
    public void updateCredentialDirectly(UserCredentialValueModel credModel) {
        CredentialEntity credentialEntity = getCredentialEntity(user, credModel.getType());

        if (credentialEntity == null) {
            credentialEntity = new CredentialEntity();
            credentialEntity.setId(KeycloakModelUtils.generateId());
            credentialEntity.setType(credModel.getType());
            credentialEntity.setCreatedDate(credModel.getCreatedDate());
            credentialEntity.setUser(user);
            em.persist(credentialEntity);
            user.getCredentials().add(credentialEntity);
        }

        credentialEntity.setValue(credModel.getValue());
        credentialEntity.setSalt(credModel.getSalt());
        credentialEntity.setDevice(credModel.getDevice());
        credentialEntity.setHashIterations(credModel.getHashIterations());

        em.flush();
    }

    @Override
    public boolean hasRole(RoleModel role) {
        Set<RoleModel> roles = getRoleMappings();
        return KeycloakModelUtils.hasRole(roles, role);
    }

    protected TypedQuery<UserRoleMappingEntity> getUserRoleMappingEntityTypedQuery(RoleModel role) {
        TypedQuery<UserRoleMappingEntity> query = em.createNamedQuery("userHasRole", UserRoleMappingEntity.class);
        query.setParameter("user", getUser());
        query.setParameter("roleId", role.getId());
        return query;
    }

    @Override
    public void grantRole(RoleModel role) {
        if (hasRole(role)) return;
        UserRoleMappingEntity entity = new UserRoleMappingEntity();
        entity.setUser(getUser());
        entity.setRoleId(role.getId());
        em.persist(entity);
        em.flush();
        em.detach(entity);
    }

    @Override
    public Set<RoleModel> getRealmRoleMappings() {
        Set<RoleModel> roleMappings = getRoleMappings();

        Set<RoleModel> realmRoles = new HashSet<RoleModel>();
        for (RoleModel role : roleMappings) {
            RoleContainerModel container = role.getContainer();
            if (container instanceof RealmModel) {
                realmRoles.add(role);
            }
        }
        return realmRoles;
    }


    @Override
    public Set<RoleModel> getRoleMappings() {
        // we query ids only as the role might be cached and following the @ManyToOne will result in a load
        // even if we're getting just the id.
        TypedQuery<String> query = em.createNamedQuery("userRoleMappingIds", String.class);
        query.setParameter("user", getUser());
        List<String> ids = query.getResultList();
        Set<RoleModel> roles = new HashSet<RoleModel>();
        for (String roleId : ids) {
            RoleModel roleById = realm.getRoleById(roleId);
            if (roleById == null) continue;
            roles.add(roleById);
        }
        return roles;
    }

    @Override
    public void deleteRoleMapping(RoleModel role) {
        if (user == null || role == null) return;

        TypedQuery<UserRoleMappingEntity> query = getUserRoleMappingEntityTypedQuery(role);
        List<UserRoleMappingEntity> results = query.getResultList();
        if (results.size() == 0) return;
        for (UserRoleMappingEntity entity : results) {
            em.remove(entity);
        }
        em.flush();
    }

    @Override
    public Set<RoleModel> getClientRoleMappings(ClientModel app) {
        Set<RoleModel> roleMappings = getRoleMappings();

        Set<RoleModel> roles = new HashSet<RoleModel>();
        for (RoleModel role : roleMappings) {
            RoleContainerModel container = role.getContainer();
            if (container instanceof ClientModel) {
                ClientModel appModel = (ClientModel)container;
                if (appModel.getId().equals(app.getId())) {
                   roles.add(role);
                }
            }
        }
        return roles;
    }

    @Override
    public String getFederationLink() {
        return user.getFederationLink();
    }

    @Override
    public void setFederationLink(String link) {
        user.setFederationLink(link);
    }

    @Override
    public String getServiceAccountClientLink() {
        return user.getServiceAccountClientLink();
    }

    @Override
    public void setServiceAccountClientLink(String clientInternalId) {
        user.setServiceAccountClientLink(clientInternalId);
    }

    @Override
    public void addConsent(UserConsentModel consent) {
        String clientId = consent.getClient().getId();

        UserConsentEntity consentEntity = getGrantedConsentEntity(clientId);
        if (consentEntity != null) {
            throw new ModelDuplicateException("Consent already exists for client [" + clientId + "] and user [" + user.getId() + "]");
        }

        consentEntity = new UserConsentEntity();
        consentEntity.setId(KeycloakModelUtils.generateId());
        consentEntity.setUser(user);
        consentEntity.setClientId(clientId);
        em.persist(consentEntity);
        em.flush();

        updateGrantedConsentEntity(consentEntity, consent);
    }

    @Override
    public UserConsentModel getConsentByClient(String clientId) {
        UserConsentEntity entity = getGrantedConsentEntity(clientId);
        return toConsentModel(entity);
    }

    @Override
    public List<UserConsentModel> getConsents() {
        TypedQuery<UserConsentEntity> query = em.createNamedQuery("userConsentsByUser", UserConsentEntity.class);
        query.setParameter("userId", getId());
        List<UserConsentEntity> results = query.getResultList();

        List<UserConsentModel> consents = new ArrayList<UserConsentModel>();
        for (UserConsentEntity entity : results) {
            UserConsentModel model = toConsentModel(entity);
            consents.add(model);
        }
        return consents;
    }

    @Override
    public void updateConsent(UserConsentModel consent) {
        String clientId = consent.getClient().getId();

        UserConsentEntity consentEntity = getGrantedConsentEntity(clientId);
        if (consentEntity == null) {
            throw new ModelException("Consent not found for client [" + clientId + "] and user [" + user.getId() + "]");
        }

        updateGrantedConsentEntity(consentEntity, consent);
    }

    @Override
    public boolean revokeConsentForClient(String clientId) {
        UserConsentEntity consentEntity = getGrantedConsentEntity(clientId);
        if (consentEntity == null) return false;

        em.remove(consentEntity);
        em.flush();
        return true;
    }


    private UserConsentEntity getGrantedConsentEntity(String clientId) {
        TypedQuery<UserConsentEntity> query = em.createNamedQuery("userConsentByUserAndClient", UserConsentEntity.class);
        query.setParameter("userId", getId());
        query.setParameter("clientId", clientId);
        List<UserConsentEntity> results = query.getResultList();
        if (results.size() > 1) {
            throw new ModelException("More results found for user [" + getUsername() + "] and client [" + clientId + "]");
        } else if (results.size() == 1) {
            return results.get(0);
        } else {
            return null;
        }
    }

    private UserConsentModel toConsentModel(UserConsentEntity entity) {
        if (entity == null) {
            return null;
        }

        ClientModel client = realm.getClientById(entity.getClientId());
        if (client == null) {
            throw new ModelException("Client with id " + entity.getClientId() + " is not available");
        }
        UserConsentModel model = new UserConsentModel(client);

        Collection<UserConsentRoleEntity> grantedRoleEntities = entity.getGrantedRoles();
        if (grantedRoleEntities != null) {
            for (UserConsentRoleEntity grantedRole : grantedRoleEntities) {
                RoleModel grantedRoleModel = realm.getRoleById(grantedRole.getRoleId());
                if (grantedRoleModel != null) {
                    model.addGrantedRole(grantedRoleModel);
                }
            }
        }

        Collection<UserConsentProtocolMapperEntity> grantedProtocolMapperEntities = entity.getGrantedProtocolMappers();
        if (grantedProtocolMapperEntities != null) {
            for (UserConsentProtocolMapperEntity grantedProtMapper : grantedProtocolMapperEntities) {
                ProtocolMapperModel protocolMapper = client.getProtocolMapperById(grantedProtMapper.getProtocolMapperId());
                model.addGrantedProtocolMapper(protocolMapper );
            }
        }

        return model;
    }

    // Update roles and protocolMappers to given consentEntity from the consentModel
    private void updateGrantedConsentEntity(UserConsentEntity consentEntity, UserConsentModel consentModel) {
        Collection<UserConsentProtocolMapperEntity> grantedProtocolMapperEntities = consentEntity.getGrantedProtocolMappers();
        Collection<UserConsentProtocolMapperEntity> mappersToRemove = new HashSet<UserConsentProtocolMapperEntity>(grantedProtocolMapperEntities);

        for (ProtocolMapperModel protocolMapper : consentModel.getGrantedProtocolMappers()) {
            UserConsentProtocolMapperEntity grantedProtocolMapperEntity = new UserConsentProtocolMapperEntity();
            grantedProtocolMapperEntity.setUserConsent(consentEntity);
            grantedProtocolMapperEntity.setProtocolMapperId(protocolMapper.getId());

            // Check if it's already there
            if (!grantedProtocolMapperEntities.contains(grantedProtocolMapperEntity)) {
                em.persist(grantedProtocolMapperEntity);
                em.flush();
                grantedProtocolMapperEntities.add(grantedProtocolMapperEntity);
            } else {
                mappersToRemove.remove(grantedProtocolMapperEntity);
            }
        }
        // Those mappers were no longer on consentModel and will be removed
        for (UserConsentProtocolMapperEntity toRemove : mappersToRemove) {
            grantedProtocolMapperEntities.remove(toRemove);
            em.remove(toRemove);
        }

        Collection<UserConsentRoleEntity> grantedRoleEntities = consentEntity.getGrantedRoles();
        Set<UserConsentRoleEntity> rolesToRemove = new HashSet<UserConsentRoleEntity>(grantedRoleEntities);
        for (RoleModel role : consentModel.getGrantedRoles()) {
            UserConsentRoleEntity consentRoleEntity = new UserConsentRoleEntity();
            consentRoleEntity.setUserConsent(consentEntity);
            consentRoleEntity.setRoleId(role.getId());

            // Check if it's already there
            if (!grantedRoleEntities.contains(consentRoleEntity)) {
                em.persist(consentRoleEntity);
                em.flush();
                grantedRoleEntities.add(consentRoleEntity);
            } else {
                rolesToRemove.remove(consentRoleEntity);
            }
        }
        // Those roles were no longer on consentModel and will be removed
        for (UserConsentRoleEntity toRemove : rolesToRemove) {
            grantedRoleEntities.remove(toRemove);
            em.remove(toRemove);
        }

        em.flush();
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



}
