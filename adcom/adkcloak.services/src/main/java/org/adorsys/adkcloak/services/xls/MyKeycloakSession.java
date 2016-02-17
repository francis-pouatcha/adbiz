package org.adorsys.adkcloak.services.xls;

import java.util.Set;

import org.keycloak.models.KeycloakContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.KeycloakTransactionManager;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.UserFederationManager;
import org.keycloak.models.UserProvider;
import org.keycloak.models.UserSessionProvider;
import org.keycloak.provider.Provider;

public class MyKeycloakSession implements KeycloakSession {
	private KeycloakSession adapter;
	
	public MyKeycloakSession(KeycloakSession adapter) {
		super();
		this.adapter = adapter;
	}

	@Override
	public KeycloakContext getContext() {
		return adapter.getContext();
	}

	@Override
	public KeycloakTransactionManager getTransaction() {
		return adapter.getTransaction();
	}

	@Override
	public <T extends Provider> T getProvider(Class<T> clazz) {
		return adapter.getProvider(clazz);
	}

	@Override
	public <T extends Provider> T getProvider(Class<T> clazz, String id) {
		return adapter.getProvider(clazz, id);
	}

	@Override
	public <T extends Provider> Set<String> listProviderIds(Class<T> clazz) {
		return adapter.listProviderIds(clazz);
	}

	@Override
	public <T extends Provider> Set<T> getAllProviders(Class<T> clazz) {
		return adapter.getAllProviders(clazz);
	}

	@Override
	public void enlistForClose(Provider provider) {
		adapter.enlistForClose(provider);
	}

	@Override
	public KeycloakSessionFactory getKeycloakSessionFactory() {
		return adapter.getKeycloakSessionFactory();
	}

	@Override
	public RealmProvider realms() {
		return adapter.getProvider(RealmProvider.class);
	}

	@Override
	public UserSessionProvider sessions() {
		return adapter.sessions();
	}

	@Override
	public void close() {
		adapter.close();
	}

	@Override
	public UserFederationManager users() {
		return adapter.users();
	}

	@Override
	public UserProvider userStorage() {
		return adapter.userStorage();
	}

}
