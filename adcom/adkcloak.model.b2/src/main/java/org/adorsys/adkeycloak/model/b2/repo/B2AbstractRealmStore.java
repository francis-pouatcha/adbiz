package org.adorsys.adkeycloak.model.b2.repo;

import javax.persistence.EntityManager;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adcore.be.repo.B2AbstractStore;
import org.adorsys.adkeycloak.model.b2.adapters.B2RealmProvider;

public abstract class B2AbstractRealmStore<T extends B2Entity> extends B2AbstractStore<T> {

    private B2RealmProvider reamlProvider;
	
	public B2AbstractRealmStore(B2RealmProvider reamlProvider, EntityManager em, int cacheSize) {
		super(em, cacheSize);
		this.reamlProvider = reamlProvider;
	}

	public B2RealmProvider getReamlProvider() {
		return reamlProvider;
	}

	public RealmStore getRealmStore() {
		return reamlProvider.getRealmStore();
	}

	public RoleStore getRoleStore() {
		return reamlProvider.getRoleStore();
	}

	public GroupStore getGroupStore() {
		return reamlProvider.getGroupStore();
	}

	public ClientStore getClientStore() {
		return reamlProvider.getClientStore();
	}
	public ClientTemplateStore getClientTemplateStore() {
		return reamlProvider.getClientTemplateStore();
	}

	@Override
	protected void removeStrict(String entType, String id) {
		reamlProvider.removeStrict(entType, id);
	}
}
