package org.adorsys.adkeycloak.model.b2.adapters;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adkeycloak.model.b2.repo.B2AbstractRealmStore;
import org.adorsys.adkeycloak.model.b2.repo.ClientStore;
import org.adorsys.adkeycloak.model.b2.repo.ClientTemplateStore;
import org.adorsys.adkeycloak.model.b2.repo.GroupStore;
import org.adorsys.adkeycloak.model.b2.repo.RealmStore;
import org.adorsys.adkeycloak.model.b2.repo.RoleStore;

/**
 * 
 * @author fpo
 *
 * @param <T>
 */
public abstract class B2AbstractReamlAdapter<T extends B2Entity> extends AbstractB2Adapter <T>{

	private B2AbstractRealmStore<T> b2Store;

    public B2AbstractReamlAdapter(B2AbstractRealmStore<T> b2Store) {
    	super(b2Store);
        this.b2Store = b2Store;
    }

	protected RealmStore getRealmStore() {
		return b2Store.getRealmStore();
	}

	protected RoleStore getRoleStore() {
		return b2Store.getRoleStore();
	}

	protected GroupStore getGroupStore() {
		return b2Store.getGroupStore();
	}

	protected ClientStore getClientStore() {
		return b2Store.getClientStore();
	}
	protected B2RealmProvider getRealmProvider() {
		return b2Store.getReamlProvider();
	}
	
	protected ClientTemplateStore getClientTemplateStore(){
		return b2Store.getClientTemplateStore();
	}
}
