package org.adorsys.adkcloak.cache.none;

import java.util.List;

import org.keycloak.migration.MigrationModel;
import org.keycloak.models.ClientModel;
import org.keycloak.models.ClientTemplateModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.RoleModel;
import org.keycloak.models.cache.CacheRealmProvider;

public class NoneCacheRealmProvider implements CacheRealmProvider {

	private RealmProvider delegate;

	public NoneCacheRealmProvider(KeycloakSession session) {
		super();
		delegate = session.getProvider(RealmProvider.class);
	}

	@Override
	public MigrationModel getMigrationModel() {
		return delegate.getMigrationModel();
	}

	@Override
	public RealmModel createRealm(String name) {
		return createRealm(name);
	}

	@Override
	public RealmModel createRealm(String id, String name) {
		return delegate.createRealm(id, name);
	}

	@Override
	public RealmModel getRealm(String id) {
		return delegate.getRealm(id);
	}

	@Override
	public RealmModel getRealmByName(String name) {
		return delegate.getRealmByName(name);
	}

	@Override
	public RoleModel getRoleById(String id, RealmModel realm) {
		return delegate.getRoleById(id, realm);
	}

	@Override
	public ClientModel getClientById(String id, RealmModel realm) {
		return delegate.getClientById(id, realm);
	}

	@Override
	public ClientTemplateModel getClientTemplateById(String id, RealmModel realm) {
		return delegate.getClientTemplateById(id, realm);
	}

	@Override
	public GroupModel getGroupById(String id, RealmModel realm) {
		return delegate.getGroupById(id, realm);
	}

	@Override
	public List<RealmModel> getRealms() {
		return delegate.getRealms();
	}

	@Override
	public boolean removeRealm(String id) {
		return delegate.removeRealm(id);
	}

	@Override
	public void close() {
		delegate.close();
	}

	@Override
	public RealmProvider getDelegate() {
		return delegate;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public void setEnabled(boolean enabled) {
		// Neve enable this.
	}

	@Override
	public void registerRealmInvalidation(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerApplicationInvalidation(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerClientTemplateInvalidation(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerRoleInvalidation(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerGroupInvalidation(String id) {
		// TODO Auto-generated method stub

	}

}
