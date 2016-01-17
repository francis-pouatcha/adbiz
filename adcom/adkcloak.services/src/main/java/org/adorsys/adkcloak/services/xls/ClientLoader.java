package org.adorsys.adkcloak.services.xls;

import org.adorsys.adcore.xls.CoreAbstLoader;
import org.jboss.resteasy.spi.Failure;
import org.keycloak.models.KeycloakSession;

public class ClientLoader extends CoreAbstModelLoader<ClientReprestn> {

	private RealmClient realmClient;

	public ClientLoader(KeycloakSession session,RealmClient realmClient) {
		super(session);
		this.realmClient = realmClient;
	}

	@Override
	protected ClientReprestn newObject() {
		return new ClientReprestn();
	}

	@Override
	protected CoreAbstLoader<ClientReprestn> getLoader() {
		return this;
	}

	@Override
	protected Object getIdentif(ClientReprestn t) {
		return t;
	}
	@Override
	protected ClientReprestn lookup(Object identif) {
		try {
			ClientReprestn id = (ClientReprestn) identif;
			return realmClient.findClient(id.getRealmId(), id.getId());
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
	@Override
	protected ClientReprestn update(ClientReprestn t) {
		return t;
	}
	@Override
	protected ClientReprestn create(ClientReprestn t) {
		try {
			realmClient.createClient(t);
			return realmClient.findClient(t.getRealmId(), t.getId());
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
}
