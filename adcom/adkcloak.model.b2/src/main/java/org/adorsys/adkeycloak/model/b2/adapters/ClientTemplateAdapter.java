package org.adorsys.adkeycloak.model.b2.adapters;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adorsys.adkeycloak.model.b2.jpa.B2ClientTemplateEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2RoleEntity;
import org.adorsys.adkeycloak.model.b2.repo.ClientTemplateStore;
import org.adorsys.adkeycloak.model.b2.repo.RoleStore;
import org.keycloak.models.ClientTemplateModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ModelDuplicateException;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.entities.ProtocolMapperEntity;
import org.keycloak.models.utils.KeycloakModelUtils;

public class ClientTemplateAdapter extends B2AbstractReamlAdapter<B2ClientTemplateEntity>
		implements ClientTemplateModel {

    private final B2ClientTemplateEntity clientTemplateEntity;
    private final RealmModel realm;
    private final KeycloakSession session;
	
    public ClientTemplateAdapter(KeycloakSession session, RealmModel realm, B2ClientTemplateEntity clientTemplateEntity, ClientTemplateStore store) {
        super(store);
        this.clientTemplateEntity = clientTemplateEntity;
        this.realm = realm;
        this.session=session;
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
        if(model.getId()!=null && getProtocolMapperById(model.getId())!=null) return model;
        
        ProtocolMapperEntity entity = new ProtocolMapperEntity();
        String id = model.getId() != null ? model.getId() : KeycloakModelUtils.generateId();
        
        entity.setId(id);
        entity.setProtocol(model.getProtocol());
        entity.setName(model.getName());
        entity.setProtocolMapper(model.getProtocolMapper());
        entity.setConfig(model.getConfig());
        entity.setConsentRequired(model.isConsentRequired());
        entity.setConsentText(model.getConsentText());
        getEntity().getProtocolMappers().add(entity);
        
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
	public boolean isFullScopeAllowed() {
        return getEntity().isFullScopeAllowed();
	}

	@Override
	public void setFullScopeAllowed(boolean value) {
        getEntity().setFullScopeAllowed(value);
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
	public Set<RoleModel> getRealmScopeMappings() {
        Set<RoleModel> allScopes = getScopeMappings();

        // Filter to retrieve just realm roles TODO: Maybe improve to avoid filter programmatically... Maybe have separate fields for realmRoles and appRoles on user?
        Set<RoleModel> realmRoles = new HashSet<RoleModel>();
        for (RoleModel role : allScopes) {
            B2RoleEntity roleEntity = ((RoleAdapter) role).getRole();

            if (realm.getId().equals(roleEntity.getRealmId())) {
                realmRoles.add(role);
            }
        }
        return realmRoles;
	}

	@Override
	public boolean hasScope(RoleModel role) {
        if (isFullScopeAllowed()) return true;
        Set<RoleModel> roles = getScopeMappings();
        if (roles.contains(role)) return true;

        for (RoleModel mapping : roles) {
            if (mapping.hasRole(role)) return true;
        }
        return false;
	}

	@Override
	public String getId() {
		return clientTemplateEntity.getId();
	}

	@Override
	public String getName() {
        return getEntity().getName();
	}

	@Override
	public RealmModel getRealm() {
        return realm;
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
	public boolean isFrontchannelLogout() {
        return getEntity().isFrontchannelLogout();
	}

	@Override
	public void setFrontchannelLogout(boolean flag) {
        getEntity().setFrontchannelLogout(flag);
        updateEntity();
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
	public boolean isPublicClient() {
        return getEntity().isPublicClient();
	}

	@Override
	public void setPublicClient(boolean flag) {
        getEntity().setPublicClient(flag);
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
	protected B2ClientTemplateEntity getEntity() {
		return clientTemplateEntity;
	}

}
