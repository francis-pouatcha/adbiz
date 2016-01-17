package org.adorsys.adkeycloak.model.b2.adapters;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adkeycloak.model.b2.repo.B2AbstractUserSessionStore;
import org.adorsys.adkeycloak.model.b2.repo.ClientSessionStore;
import org.adorsys.adkeycloak.model.b2.repo.UserSessionStore;

/**
 * 
 * @author fpo
 *
 * @param <T>
 */
public abstract class B2AbstractUserSessionAdapter<T extends B2Entity>  extends AbstractB2Adapter <T>{

	private B2AbstractUserSessionStore<T> b2Store;

    public B2AbstractUserSessionAdapter(B2AbstractUserSessionStore<T> b2Store) {
    	super(b2Store);
        this.b2Store = b2Store;
    }

	protected UserSessionStore getUserSessionStore() {
		return b2Store.getUserSessionStore();
	}

	protected ClientSessionStore getClientSessionStore() {
		return b2Store.getClientSessionStore();
	}
}
