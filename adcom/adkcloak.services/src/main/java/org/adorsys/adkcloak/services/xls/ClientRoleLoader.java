package org.adorsys.adkcloak.services.xls;

import org.adorsys.adcore.xls.CoreAbstLoader;
import org.jboss.resteasy.spi.Failure;
import org.keycloak.models.KeycloakSession;

public class ClientRoleLoader extends CoreAbstModelLoader<ClientRoleReprestn> {

	private RealmClient realmClient;

	public ClientRoleLoader(KeycloakSession session,RealmClient realmClient) {
		super(session);
		this.realmClient = realmClient;
	}

	@Override
	protected ClientRoleReprestn newObject() {
		return new ClientRoleReprestn();
	}

	@Override
	protected CoreAbstLoader<ClientRoleReprestn> getLoader() {
		return this;
	}

	@Override
	protected Object getIdentif(ClientRoleReprestn t) {
		return t;
	}
	@Override
	protected ClientRoleReprestn lookup(Object identif) {
		try {
			ClientRoleReprestn clientRoleRep = (ClientRoleReprestn) identif;
			return realmClient.findClientRole(clientRoleRep.getRealmId(), clientRoleRep.getClientId(),clientRoleRep.getName());
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
	@Override
	protected ClientRoleReprestn update(ClientRoleReprestn t) {
		return t;
	}
	@Override
	protected ClientRoleReprestn create(ClientRoleReprestn t) {
		try {
			realmClient.createClientRole(t);
			return realmClient.findClientRole(t.getRealmId(), t.getClientId(),t.getName());
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
}
