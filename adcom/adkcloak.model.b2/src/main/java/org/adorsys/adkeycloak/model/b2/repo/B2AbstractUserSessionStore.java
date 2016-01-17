package org.adorsys.adkeycloak.model.b2.repo;

import javax.persistence.EntityManager;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adcore.be.repo.B2AbstractStore;
import org.adorsys.adkeycloak.model.b2.adapters.B2UserSessionPersisterProvider;

public abstract class B2AbstractUserSessionStore<T extends B2Entity> extends B2AbstractStore<T> {

    private B2UserSessionPersisterProvider userSessionProvider;
	
	public B2AbstractUserSessionStore(B2UserSessionPersisterProvider userSessionProvider, EntityManager em, int cacheSize) {
		super(em, cacheSize);
		this.userSessionProvider = userSessionProvider;
	}

	@Override
	protected void removeStrict(String entType, String id) {
		userSessionProvider.removeStrict(entType, id);
	}

	public UserSessionStore getUserSessionStore(){
		return userSessionProvider.getUserSessionStore();
	}
	public ClientSessionStore getClientSessionStore(){
		return userSessionProvider.getClientSessionStore();
	}
}
