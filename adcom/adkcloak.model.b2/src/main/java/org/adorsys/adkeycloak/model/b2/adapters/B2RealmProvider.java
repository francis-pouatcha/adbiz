package org.adorsys.adkeycloak.model.b2.adapters;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.adorsys.adkeycloak.model.b2.jpa.B2ClientEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2ClientTemplateEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2GroupEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2RealmEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2RoleEntity;
import org.adorsys.adkeycloak.model.b2.repo.ClientStore;
import org.adorsys.adkeycloak.model.b2.repo.ClientTemplateStore;
import org.adorsys.adkeycloak.model.b2.repo.GroupStore;
import org.adorsys.adkeycloak.model.b2.repo.RealmStore;
import org.adorsys.adkeycloak.model.b2.repo.RoleStore;
import org.keycloak.migration.MigrationModel;
import org.keycloak.models.ClientModel;
import org.keycloak.models.ClientTemplateModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.RoleModel;
import org.keycloak.models.utils.KeycloakModelUtils;

/**
 * 
 * @author fpo
 *
 */
public class B2RealmProvider implements RealmProvider {

    private final KeycloakSession session;
    private final RealmStore realmStore;
    private final RoleStore roleStore;
    private final GroupStore groupStore;
    private final ClientStore clientStore;
    private final ClientTemplateStore clientTemplateStore;

    private EntityManager em;

    public B2RealmProvider(KeycloakSession session, EntityManager em) {
        this.session = session;
        this.em = em;
        this.realmStore = new RealmStore(this, em, 50);
        this.roleStore = new RoleStore(this, em, 1000);
        this.groupStore = new GroupStore(this, em, 1000);
        this.clientStore = new ClientStore(this, em, 1000);
        this.clientTemplateStore = new ClientTemplateStore(this, em, 100);
    }

    @Override
    public void close() {
        // TODO Flush cache
    }

    @Override
    public MigrationModel getMigrationModel() {
        return new MigrationModelAdapter(em);
    }

    @Override
    public RealmModel createRealm(String name) {
        return createRealm(KeycloakModelUtils.generateId(), name);
    }

    @Override
    public RealmModel createRealm(String id, String name) {
        B2RealmEntity newRealm = new B2RealmEntity();
        newRealm.setId(id);
        newRealm.setName(name);
        realmStore.save(newRealm);

        final RealmModel model = new RealmAdapter(session, newRealm, realmStore);
        session.getKeycloakSessionFactory().publish(new RealmModel.RealmCreationEvent() {
            @Override
            public RealmModel getCreatedRealm() {
                return model;
            }
        });
        return model;
    }

    @Override
    public RealmModel getRealm(String id) {
        B2RealmEntity realmEntity = realmStore.load(id);
        return realmEntity != null ? new RealmAdapter(session, realmEntity, realmStore) : null;
    }

    @Override
    public List<RealmModel> getRealms() {
    	List<B2RealmEntity> realms = realmStore.listAll();
        List<RealmModel> results = new ArrayList<RealmModel>();
        for (B2RealmEntity realmEntity : realms) {
            results.add(new RealmAdapter(session, realmEntity, realmStore));
        }
        return results;
    }

    @Override
    public RealmModel getRealmByName(String name) {
    	B2RealmEntity realm = realmStore.findByName(name);
        if (realm == null) return null;
        return new RealmAdapter(session, realm, realmStore);
    }

    @Override
    public boolean removeRealm(String id) {
        RealmModel realm = getRealm(id);
        if (realm == null) return false;
        session.users().preRemove(realm);
        return realmStore.remove(id);
    }

    @Override
    public RoleModel getRoleById(String id, RealmModel realm) {
    	B2RoleEntity role = roleStore.load(id);
        if (role == null) return null;
        if (role.getRealmId() != null && !role.getRealmId().equals(realm.getId())) return null;
        if (role.getClientId() != null && realm.getClientById(role.getClientId()) == null) return null;
        return new RoleAdapter(session, realm, role, roleStore);
    }

    @Override
    public GroupModel getGroupById(String id, RealmModel realm) {
        B2GroupEntity group = groupStore.load(id);
        if (group == null) return null;
        if (group.getRealmId() != null && !group.getRealmId().equals(realm.getId())) return null;
        return new GroupAdapter(session, realm, group, groupStore);
    }

    @Override
    public ClientModel getClientById(String id, RealmModel realm) {
        B2ClientEntity appData = clientStore.load(id);

        // Check if application belongs to this realm
        if (appData == null || !realm.getId().equals(appData.getRealmId())) {
            return null;
        }

        return new ClientAdapter(session, realm, appData, clientStore);
    }

	public void removeStrict(String entType, String id) {
		if(B2RealmEntity.ENT_TYPE.equals(entType)) {
			realmStore.removeStrict(id);
		} else if(B2ClientEntity.ENT_TYPE.equals(entType)){
			clientStore.removeStrict(id);
		} else if(B2GroupEntity.ENT_TYPE.equals(entType)){
			groupStore.removeStrict(id);
		} else if(B2RoleEntity.ENT_TYPE.equals(entType)){
			roleStore.removeStrict(id);
		}
	}

	public RealmStore getRealmStore() {
		return realmStore;
	}

	public RoleStore getRoleStore() {
		return roleStore;
	}

	public GroupStore getGroupStore() {
		return groupStore;
	}

	public ClientStore getClientStore() {
		return clientStore;
	}

	public ClientTemplateStore getClientTemplateStore() {
		return clientTemplateStore;
	}

	@Override
	public ClientTemplateModel getClientTemplateById(String clientTemplateById, RealmModel realm) {
		B2ClientTemplateEntity clientTemplateEntity = clientTemplateStore.load(clientTemplateById);
		return new ClientTemplateAdapter(session, realm, clientTemplateEntity, clientTemplateStore); 
	}
}
