package org.adorsys.adkcloak.services.xls;

import org.adorsys.adcore.xls.CoreAbstLoader;
import org.keycloak.models.KeycloakSession;
import org.keycloak.representations.idm.RealmRepresentation;

public class RealmLoader extends CoreAbstModelLoader<RealmRepresentation> {

	private RealmClient realmClient;

	public RealmLoader(KeycloakSession session,RealmClient realmClient) {
		super(session);
		this.realmClient = realmClient;
	}

	@Override
	protected RealmRepresentation newObject() {
		return new RealmRepresentation();
	}

	@Override
	protected CoreAbstLoader<RealmRepresentation> getLoader() {
		return this;
	}

	@Override
	protected Object getIdentif(RealmRepresentation t) {
		return t.getId();
	}
	@Override
	protected RealmRepresentation lookup(Object identif) {
		return realmClient.findRealm((String) identif);
	}
	@Override
	protected RealmRepresentation update(RealmRepresentation t) {
		return t;
	}
	@Override
	protected RealmRepresentation create(RealmRepresentation t) {
		return realmClient.createRealm(t);
	}
	
}
