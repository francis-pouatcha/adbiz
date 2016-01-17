package org.adorsys.adkcloak.services.xls;

import org.adorsys.adcore.xls.CoreAbstLoader;
import org.jboss.resteasy.spi.Failure;
import org.keycloak.models.KeycloakSession;

public class UserLoader extends CoreAbstModelLoader<UserReprestn> {

	private RealmClient realmClient;

	public UserLoader(KeycloakSession session,RealmClient realmClient) {
		super(session);
		this.realmClient = realmClient;
	}

	@Override
	protected UserReprestn newObject() {
		return new UserReprestn();
	}

	@Override
	protected CoreAbstLoader<UserReprestn> getLoader() {
		return this;
	}

	@Override
	protected Object getIdentif(UserReprestn t) {
		return t;
	}
	@Override
	protected UserReprestn lookup(Object identif) {
		try {
			UserReprestn id = (UserReprestn) identif;
			return realmClient.findUser(id.getRealmId(), id.getUsername());
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
	@Override
	protected UserReprestn update(UserReprestn t) {
		return t;
	}
	@Override
	protected UserReprestn create(UserReprestn t) {
		try {
			realmClient.createUser(t);
			return realmClient.findUser(t.getRealmId(), t.getUsername());
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
}
