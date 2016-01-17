package org.adorsys.adkeycloak.model.b2.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adorsys.adkeycloak.model.b2.jpa.B2ClientEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2RealmEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2RoleEntity;
import org.adorsys.adkeycloak.model.b2.repo.B2AbstractRealmStore;
import org.adorsys.adkeycloak.model.b2.repo.ClientStore;
import org.adorsys.adkeycloak.model.b2.repo.RealmStore;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleContainerModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.utils.KeycloakModelUtils;

public class RoleAdapter extends B2AbstractReamlAdapter<B2RoleEntity> implements RoleModel {

    private final B2RoleEntity role;
    private RoleContainerModel roleContainer;
    private RealmModel realm;
    private KeycloakSession session;

    public RoleAdapter(KeycloakSession session, RealmModel realm, B2RoleEntity roleEntity, B2AbstractRealmStore<B2RoleEntity> store) {
        this(session, realm, roleEntity, null, store);
    }

    public RoleAdapter(KeycloakSession session, RealmModel realm, B2RoleEntity roleEntity, RoleContainerModel roleContainer, B2AbstractRealmStore<B2RoleEntity> store) {
        super(store);
        this.role = roleEntity;
        this.roleContainer = roleContainer;
        this.realm = realm;
        this.session = session;
    }

    @Override
    public String getId() {
        return role.getId();
    }

    @Override
    public String getName() {
        return role.getName();
    }

    @Override
    public void setName(String name) {
        role.setName(name);
        updateRole();
    }

    @Override
    public String getDescription() {
        return role.getDescription();
    }

    @Override
    public void setDescription(String description) {
        role.setDescription(description);
        updateRole();
    }

    @Override
    public boolean isScopeParamRequired() {
        return role.isScopeParamRequired();
    }

    @Override
    public void setScopeParamRequired(boolean scopeParamRequired) {
        role.setScopeParamRequired(scopeParamRequired);
        updateRole();
    }

    @Override
    public boolean isComposite() {
        return role.getCompositeRoleIds() != null && role.getCompositeRoleIds().size() > 0;
    }

    protected void updateRole() {
        super.updateEntity();
    }

    @Override
    public void addCompositeRole(RoleModel childRole) {
    	if(getEntity().getCompositeRoleIds()==null)getEntity().setCompositeRoleIds(new ArrayList<String>());
    	if(!getEntity().getCompositeRoleIds().contains(childRole.getId())){
    		getEntity().getCompositeRoleIds().add(childRole.getId());
    		updateEntity();
    	}
    }

    @Override
    public void removeCompositeRole(RoleModel childRole) {
    	if(getEntity().getCompositeRoleIds()==null)getEntity().setCompositeRoleIds(new ArrayList<String>());
    	if(getEntity().getCompositeRoleIds().contains(childRole.getId())){
    		getEntity().getCompositeRoleIds().remove(childRole.getId());
    		updateEntity();
    	}
    }

    @Override
    public Set<RoleModel> getComposites() {
        if (role.getCompositeRoleIds() == null || role.getCompositeRoleIds().isEmpty()) {
            return Collections.emptySet();
        }

        List<B2RoleEntity> childRoles = getRoleStore().load(role.getCompositeRoleIds());

        Set<RoleModel> set = new HashSet<RoleModel>();
        for (B2RoleEntity childRole : childRoles) {
            set.add(new RoleAdapter(session, realm, childRole, getRoleStore()));
        }
        return set;
    }

    @Override
    public RoleContainerModel getContainer() {
        if (roleContainer == null) {
            // Compute it
            if (role.isRealmRole()) {
            	RealmStore realmStore = getRealmStore();
                B2RealmEntity realm = realmStore.load(role.getRealmId());
                if (realm == null) {
                    throw new IllegalStateException("Realm with id: " + role.getRealmId() + " doesn't exists");
                }
                roleContainer = new RealmAdapter(session, realm, realmStore);
            } else if (role.isClientRole()) {
            	ClientStore clientStore = getClientStore();
                B2ClientEntity appEntity = clientStore.load(role.getClientId());
                if (appEntity == null) {
                    throw new IllegalStateException("Application with id: " + role.getClientId() + " doesn't exists");
                }
                roleContainer = new ClientAdapter(session, realm, appEntity, clientStore);
            } else {
                throw new IllegalStateException("Neither realm role nor a client role: " + this);
            }
        }
        return roleContainer;
    }

    @Override
    public boolean hasRole(RoleModel role) {
        if (this.equals(role)) return true;
        if (!isComposite()) return false;

        Set<RoleModel> visited = new HashSet<RoleModel>();
        return KeycloakModelUtils.searchFor(role, this, visited);
    }

    public B2RoleEntity getRole() {
        return role;
    }

    @Override
    public B2RoleEntity getEntity() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof RoleModel)) return false;

        RoleModel that = (RoleModel) o;
        return that.getId().equals(getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
