package org.adorsys.adkeycloak.model.b2.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adorsys.adkeycloak.model.b2.jpa.B2ClientEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2ClientTemplateEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2RoleEntity;
import org.adorsys.adkeycloak.model.b2.repo.ClientStore;
import org.adorsys.adkeycloak.model.b2.repo.RoleStore;
import org.keycloak.models.ClientModel;
import org.keycloak.models.ClientTemplateModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ModelDuplicateException;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.entities.ProtocolMapperEntity;
import org.keycloak.models.utils.KeycloakModelUtils;

public class ClientAdapter extends B2AbstractReamlAdapter<B2ClientEntity> implements ClientModel {

    protected final B2ClientEntity clientEntity;
    private final RealmModel realm;
    protected  KeycloakSession session;

    public ClientAdapter(KeycloakSession session, RealmModel realm, B2ClientEntity clientEntity, ClientStore store) {
        super(store);
        this.session = session;
        this.realm = realm;
        this.clientEntity = clientEntity;
    }

    @Override
    public B2ClientEntity getEntity() {
        return clientEntity;
    }

    @Override
    public void updateClient() {
        updateEntity();
    }


    @Override
    public String getId() {
        return getEntity().getId();
    }

    @Override
    public String getClientId() {
        return getEntity().getClientId();
    }

    @Override
    public String getName() {
        return getEntity().getName();
    }

    @Override
    public void setName(String name) {
        getEntity().setName(name);
        updateEntity();
    }

    @Override
    public String getDescription() { return getEntity().getDescription(); }

    @Override
    public void setDescription(String description) {
        getEntity().setDescription(description);
        updateEntity();
    }

    @Override
    public void setClientId(String clientId) {
        getEntity().setClientId(clientId);
        updateEntity();
    }

    @Override
    public Set<String> getWebOrigins() {
        Set<String> result = new HashSet<String>();
        if (getEntity().getWebOrigins() != null) {
            result.addAll(getEntity().getWebOrigins());
        }
        return result;
    }

    @Override
    public void setWebOrigins(Set<String> webOrigins) {
        List<String> result = new ArrayList<String>();
        result.addAll(webOrigins);
        getEntity().setWebOrigins(result);
        updateEntity();
    }

    @Override
    public void addWebOrigin(String webOrigin) {
    	if(!getEntity().getWebOrigins().contains(webOrigin)){
    		getEntity().getWebOrigins().add(webOrigin);
            updateEntity();
    	}
    }

    @Override
    public void removeWebOrigin(String webOrigin) {
    	if(getEntity().getWebOrigins().contains(webOrigin)){
    		getEntity().getWebOrigins().remove(webOrigin);
            updateEntity();
    	}
    }

    @Override
    public Set<String> getRedirectUris() {
        Set<String> result = new HashSet<String>();
        if (getEntity().getRedirectUris() != null) {
            result.addAll(getEntity().getRedirectUris());
        }
        return result;
    }

    @Override
    public void setRedirectUris(Set<String> redirectUris) {
        List<String> result = new ArrayList<String>();
        result.addAll(redirectUris);
        getEntity().setRedirectUris(result);
        updateEntity();
    }

    @Override
    public void addRedirectUri(String redirectUri) {
    	if(!getEntity().getRedirectUris().contains(redirectUri)){
    		getEntity().getRedirectUris().add(redirectUri);
            updateEntity();
    	}
    }

    @Override
    public void removeRedirectUri(String redirectUri) {
    	if(getEntity().getRedirectUris().contains(redirectUri)){
    		getEntity().getRedirectUris().remove(redirectUri);
            updateEntity();
    	}
    }

    @Override
    public boolean isEnabled() {
        return getEntity().isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        getEntity().setEnabled(enabled);
        updateEntity();
    }

    @Override
    public String getClientAuthenticatorType() {
        return getEntity().getClientAuthenticatorType();
    }

    @Override
    public void setClientAuthenticatorType(String clientAuthenticatorType) {
        getEntity().setClientAuthenticatorType(clientAuthenticatorType);
        updateEntity();
    }

    @Override
    public boolean validateSecret(String secret) {
        return secret.equals(getEntity().getSecret());
    }

    @Override
    public String getSecret() {
        return getEntity().getSecret();
    }

    @Override
    public void setSecret(String secret) {
        getEntity().setSecret(secret);
        updateEntity();
    }

    @Override
    public String getRegistrationToken() {
        return getEntity().getRegistrationToken();
    }

    @Override
    public void setRegistrationToken(String registrationToken) {
        getEntity().setRegistrationToken(registrationToken);
        updateEntity();
    }

    @Override
    public boolean isPublicClient() {
        return getEntity().isPublicClient();
    }

    @Override
    public void setPublicClient(boolean flag) {
        getEntity().setPublicClient(flag);
        updateEntity();
    }


    @Override
    public boolean isFrontchannelLogout() {
        return getEntity().isFrontchannelLogout();
    }

    @Override
    public void setFrontchannelLogout(boolean flag) {
        getEntity().setFrontchannelLogout(flag);
        updateEntity();
    }

    @Override
    public boolean isFullScopeAllowed() {
        return getEntity().isFullScopeAllowed();
    }

    @Override
    public void setFullScopeAllowed(boolean value) {
        getEntity().setFullScopeAllowed(value);
        updateEntity();

    }

    @Override
    public RealmModel getRealm() {
        return realm;
    }

    @Override
    public int getNotBefore() {
        return getEntity().getNotBefore();
    }

    @Override
    public void setNotBefore(int notBefore) {
        getEntity().setNotBefore(notBefore);
        updateEntity();
    }

    @Override
    public Set<RoleModel> getScopeMappings() {
    	List<String> scopeIds = getEntity().getScopeIds();
    	RoleStore roleStore = getRoleStore();
        Set<RoleModel> result = new HashSet<RoleModel>();
        List<B2RoleEntity> roles = roleStore.load(scopeIds);

        for (B2RoleEntity role : roles) {
            if (realm.getId().equals(role.getRealmId())) {
                result.add(new RoleAdapter(session, realm, role, realm, roleStore));
            } else {
                // Likely applicationRole, but we don't have this application yet
                result.add(new RoleAdapter(session, realm, role, roleStore));
            }
        }
        return result;
    }

    @Override
    public Set<RoleModel> getRealmScopeMappings() {
    	List<String> scopeIds = getEntity().getScopeIds();
    	RoleStore roleStore = getRoleStore();
        Set<RoleModel> result = new HashSet<RoleModel>();
        List<B2RoleEntity> roles = roleStore.load(scopeIds);

        for (B2RoleEntity role : roles) {
            if (realm.getId().equals(role.getRealmId())) {
                result.add(new RoleAdapter(session, realm, role, realm, roleStore));
            }
        }
        return result;
    }

    @Override
    public void addScopeMapping(RoleModel role) {
    	if(!getEntity().getScopeIds().contains(role.getId())){
    		getEntity().getScopeIds().add(role.getId());
    		updateEntity();
    	}
    }

    @Override
    public void deleteScopeMapping(RoleModel role) {
    	if(getEntity().getScopeIds().contains(role.getId())){
    		getEntity().getScopeIds().remove(role.getId());
    		updateEntity();
    	}
    }

    @Override
    public String getProtocol() {
        return getEntity().getProtocol();
    }

    @Override
    public void setProtocol(String protocol) {
        getEntity().setProtocol(protocol);
        updateEntity();

    }

    @Override
    public void setAttribute(String name, String value) {
        getEntity().getAttributes().put(name, value);
        updateEntity();

    }

    @Override
    public void removeAttribute(String name) {
        getEntity().getAttributes().remove(name);
        updateEntity();
    }

    @Override
    public String getAttribute(String name) {
        return getEntity().getAttributes().get(name);
    }

    @Override
    public Map<String, String> getAttributes() {
        Map<String, String> copy = new HashMap<String, String>();
        copy.putAll(getEntity().getAttributes());
        return copy;
    }

    @Override
    public Set<ProtocolMapperModel> getProtocolMappers() {
        Set<ProtocolMapperModel> result = new HashSet<ProtocolMapperModel>();
        for (ProtocolMapperEntity entity : getEntity().getProtocolMappers()) {
            ProtocolMapperModel mapping = new ProtocolMapperModel();
            mapping.setId(entity.getId());
            mapping.setName(entity.getName());
            mapping.setProtocol(entity.getProtocol());
            mapping.setProtocolMapper(entity.getProtocolMapper());
            mapping.setConsentRequired(entity.isConsentRequired());
            mapping.setConsentText(entity.getConsentText());
            Map<String, String> config = new HashMap<String, String>();
            if (entity.getConfig() != null) {
                config.putAll(entity.getConfig());
            }
            mapping.setConfig(config);
            result.add(mapping);
        }
        return result;
    }

    @Override
    public ProtocolMapperModel addProtocolMapper(ProtocolMapperModel model) {
        if (getProtocolMapperByName(model.getProtocol(), model.getName()) != null) {
            throw new ModelDuplicateException("Protocol mapper name must be unique per protocol");
        }
        String id = model.getId();
    	ProtocolMapperEntity entity = getProtocolMapperyEntityById(id);
    	if(entity==null){
            entity = new ProtocolMapperEntity();
            if(id==null)id = KeycloakModelUtils.generateId();

            entity.setId(id);
            getEntity().getProtocolMappers().add(entity);
    	}        

    	// update payload.
    	entity.setProtocol(model.getProtocol());
        entity.setName(model.getName());
        entity.setProtocolMapper(model.getProtocolMapper());
        entity.setConfig(model.getConfig());
        entity.setConsentRequired(model.isConsentRequired());
        entity.setConsentText(model.getConsentText());
        
        updateEntity();
        return entityToModel(entity);
    }

    @Override
    public void removeProtocolMapper(ProtocolMapperModel mapping) {
    	ProtocolMapperEntity entityToRemove = getProtocolMapperyEntityById(mapping.getId());

    	if(entityToRemove!=null){
    		getEntity().getProtocolMappers().remove(entityToRemove);
    		session.users().preRemove(mapping);
    		updateEntity();
    	}
    }

    protected ProtocolMapperEntity getProtocolMapperyEntityById(String id) {
    	if(id==null) return null;
        for (ProtocolMapperEntity entity : getEntity().getProtocolMappers()) {
            if (id.equals(entity.getId())) {
                return entity;
            }
        }
        return null;

    }
    protected ProtocolMapperEntity getProtocolMapperEntityByName(String protocol, String name) {
        for (ProtocolMapperEntity entity : getEntity().getProtocolMappers()) {
            if (entity.getProtocol().equals(protocol) && entity.getName().equals(name)) {
                return entity;
            }
        }
        return null;

    }


    @Override
    public void updateProtocolMapper(ProtocolMapperModel mapping) {
        ProtocolMapperEntity entity = getProtocolMapperyEntityById(mapping.getId());
        entity.setProtocolMapper(mapping.getProtocolMapper());
        entity.setConsentRequired(mapping.isConsentRequired());
        entity.setConsentText(mapping.getConsentText());
        if (entity.getConfig() != null) {
            entity.getConfig().clear();
            entity.getConfig().putAll(mapping.getConfig());
        } else {
            entity.setConfig(mapping.getConfig());
        }
        updateEntity();

    }

    @Override
    public ProtocolMapperModel getProtocolMapperById(String id) {
        ProtocolMapperEntity entity = getProtocolMapperyEntityById(id);
        if (entity == null) return null;
        return entityToModel(entity);
    }

    @Override
    public ProtocolMapperModel getProtocolMapperByName(String protocol, String name) {
        ProtocolMapperEntity entity = getProtocolMapperEntityByName(protocol, name);
        if (entity == null) return null;
        return entityToModel(entity);
    }

    protected ProtocolMapperModel entityToModel(ProtocolMapperEntity entity) {
        ProtocolMapperModel mapping = new ProtocolMapperModel();
        mapping.setId(entity.getId());
        mapping.setName(entity.getName());
        mapping.setProtocol(entity.getProtocol());
        mapping.setProtocolMapper(entity.getProtocolMapper());
        mapping.setConsentRequired(entity.isConsentRequired());
        mapping.setConsentText(entity.getConsentText());
        Map<String, String> config = new HashMap<String, String>();
        if (entity.getConfig() != null) config.putAll(entity.getConfig());
        mapping.setConfig(config);
        return mapping;
    }


    @Override
    public boolean isSurrogateAuthRequired() {
        return getEntity().isSurrogateAuthRequired();
    }

    @Override
    public void setSurrogateAuthRequired(boolean surrogateAuthRequired) {
        getEntity().setSurrogateAuthRequired(surrogateAuthRequired);
        updateEntity();
    }

    @Override
    public String getManagementUrl() {
        return getEntity().getManagementUrl();
    }

    @Override
    public void setManagementUrl(String url) {
        getEntity().setManagementUrl(url);
        updateEntity();
    }

    @Override
    public void setRootUrl(String url) {
        getEntity().setRootUrl(url);
        updateEntity();
    }

    @Override
    public String getRootUrl() {
        return getEntity().getRootUrl();
    }

    @Override
    public void setBaseUrl(String url) {
        getEntity().setBaseUrl(url);
        updateEntity();
    }

    @Override
    public String getBaseUrl() {
        return getEntity().getBaseUrl();
    }

    @Override
    public boolean isBearerOnly() {
        return getEntity().isBearerOnly();
    }

    @Override
    public void setBearerOnly(boolean only) {
        getEntity().setBearerOnly(only);
        updateEntity();
    }

    @Override
    public boolean isConsentRequired() {
        return getEntity().isConsentRequired();
    }

    @Override
    public void setConsentRequired(boolean consentRequired) {
        getEntity().setConsentRequired(consentRequired);
        updateEntity();
    }

    @Override
    public boolean isStandardFlowEnabled() {
        return getEntity().isStandardFlowEnabled();
    }

    @Override
    public void setStandardFlowEnabled(boolean standardFlowEnabled) {
        getEntity().setStandardFlowEnabled(standardFlowEnabled);
        updateEntity();
    }

    @Override
    public boolean isImplicitFlowEnabled() {
        return getEntity().isImplicitFlowEnabled();
    }

    @Override
    public void setImplicitFlowEnabled(boolean implicitFlowEnabled) {
        getEntity().setImplicitFlowEnabled(implicitFlowEnabled);
        updateEntity();
    }

    @Override
    public boolean isDirectAccessGrantsEnabled() {
        return getEntity().isDirectAccessGrantsEnabled();
    }

    @Override
    public void setDirectAccessGrantsEnabled(boolean directAccessGrantsEnabled) {
        getEntity().setDirectAccessGrantsEnabled(directAccessGrantsEnabled);
        updateEntity();
    }

    @Override
    public boolean isServiceAccountsEnabled() {
        return getEntity().isServiceAccountsEnabled();
    }

    @Override
    public void setServiceAccountsEnabled(boolean serviceAccountsEnabled) {
        getEntity().setServiceAccountsEnabled(serviceAccountsEnabled);
        updateEntity();
    }

    @Override
    public RoleAdapter getRole(String name) {
    	RoleStore roleStore = getRoleStore();
    	B2RoleEntity role = roleStore.findByNameAndClientId(name, getId());
        if (role == null) {
            return null;
        } else {
            return new RoleAdapter(session, getRealm(), role, roleStore);
        }
    }

    @Override
    public RoleAdapter addRole(String name) {
        return this.addRole(null, name);
    }

    @Override
    public RoleAdapter addRole(String id, String name) {
    	RoleStore roleStore = getRoleStore();
        B2RoleEntity roleEntity = new B2RoleEntity();
        if(id==null)id=KeycloakModelUtils.generateId();
        roleEntity.setId(id);
        roleEntity.setName(name);
        roleEntity.setClientId(getId());
        roleEntity.setRootId(getRealm().getId());
        roleEntity.setRoleType(B2RoleEntity.ROLE_TYPE_CLIENT);
        roleEntity.setCtnrId(getId());
        roleStore.save(roleEntity);
        
        return new RoleAdapter(session, getRealm(), roleEntity, this, roleStore);
    }

    @Override
    public boolean removeRole(RoleModel role) {
    	RoleStore roleStore = getRoleStore();
        session.users().preRemove(getRealm(), role);
        return roleStore.remove(role.getId());
    }

    @Override
    public Set<RoleModel> getRoles() {
    	RoleStore roleStore = getRoleStore();
    	Long count = roleStore.countByClientId(getId());
    	List<B2RoleEntity> roles = roleStore.findByClientId(getId(), 0, count.intValue());
        Set<RoleModel> result = new HashSet<RoleModel>();
        for (B2RoleEntity role : roles) {
            result.add(new RoleAdapter(session, getRealm(), role, this, roleStore));
        }
        return result;
    }

    @Override
    public boolean hasScope(RoleModel role) {
        if (isFullScopeAllowed()) return true;
        Set<RoleModel> roles = getScopeMappings();
        if (roles.contains(role)) return true;

        for (RoleModel mapping : roles) {
            if (mapping.hasRole(role)) return true;
        }

        roles = getRoles();
        if (roles.contains(role)) return true;

        for (RoleModel mapping : roles) {
            if (mapping.hasRole(role)) return true;
        }
        return false;
    }

    @Override
    public List<String> getDefaultRoles() {
        return getEntity().getDefaultRoles();
    }

    @Override
    public void addDefaultRole(String name) {
    	if(getEntity().getDefaultRoles().contains(name)) return;
    	
        RoleModel role = getRole(name);
        if (role == null) {
            addRole(name);
        }
        getEntity().getDefaultRoles().add(name);
        updateEntity();
    }

    @Override
    public void updateDefaultRoles(String[] defaultRoles) {
        List<String> roleNames = new ArrayList<String>();
        for (String roleName : defaultRoles) {
            RoleModel role = getRole(roleName);
            if (role == null) {
                addRole(roleName);
            }

            roleNames.add(roleName);
        }

        getEntity().setDefaultRoles(roleNames);
        updateEntity();
    }

    @Override
    public int getNodeReRegistrationTimeout() {
        return getEntity().getNodeReRegistrationTimeout();
    }

    @Override
    public void setNodeReRegistrationTimeout(int timeout) {
        getEntity().setNodeReRegistrationTimeout(timeout);
        updateEntity();
    }

    @Override
    public Map<String, Integer> getRegisteredNodes() {
        return getEntity().getRegisteredNodes() == null ? Collections.<String, Integer>emptyMap() : Collections.unmodifiableMap(getEntity().getRegisteredNodes());
    }

    @Override
    public void registerNode(String nodeHost, int registrationTime) {
        B2ClientEntity entity = getEntity();
        if (entity.getRegisteredNodes() == null) {
            entity.setRegisteredNodes(new HashMap<String, Integer>());
        }

        entity.getRegisteredNodes().put(nodeHost, registrationTime);
        updateEntity();
    }

    @Override
    public void unregisterNode(String nodeHost) {
        B2ClientEntity entity = getEntity();
        if (entity.getRegisteredNodes() == null) return;

        entity.getRegisteredNodes().remove(nodeHost);
        updateEntity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof ClientModel)) return false;

        ClientModel that = (ClientModel) o;
        return that.getId().equals(getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

	@Override
	public ClientTemplateModel getClientTemplate() {
		String clientTemplate = getEntity().getClientTemplate();
		B2ClientTemplateEntity clientTemplateEntity = getClientTemplateStore().findByRealmIdAndName(getEntity().getRealmId(), clientTemplate);
		if(clientTemplateEntity==null) return null;
		return new ClientTemplateAdapter(session, realm, clientTemplateEntity, getClientTemplateStore());
	}

	@Override
	public void setClientTemplate(ClientTemplateModel clientTemplateModel) {
		getEntity().setClientTemplate(clientTemplateModel.getName());
        updateEntity();
	}

	@Override
	public void setUseTemplateConfig(boolean useTemplateConfig) {
		getEntity().setUseTemplateConfig(useTemplateConfig);
        updateEntity();
	}

	@Override
	public void setUseTemplateMappers(boolean useTemplateMappers) {
		getEntity().setUseTemplateMappers(useTemplateMappers);
		updateEntity();
	}

	@Override
	public void setUseTemplateScope(boolean useTemplateScope) {
		getEntity().setUseTemplateScope(useTemplateScope);
		updateEntity();
	}

	@Override
	public boolean useTemplateConfig() {
		return getEntity().isUseTemplateConfig();
	}

	@Override
	public boolean useTemplateMappers() {
		return getEntity().isUseTemplateMappers();
	}

	@Override
	public boolean useTemplateScope() {
		return getEntity().isUseTemplateScope();
	}

}
