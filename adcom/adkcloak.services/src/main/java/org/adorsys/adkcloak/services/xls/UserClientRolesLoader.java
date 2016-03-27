package org.adorsys.adkcloak.services.xls;

import org.adorsys.adcore.xls.CoreAbstLoader;
import org.keycloak.models.KeycloakSession;

public class UserClientRolesLoader extends CoreAbstModelLoader<UserClientRoles> {

	private RealmClient realmClient;

	public UserClientRolesLoader(KeycloakSession session,RealmClient realmClient) {
		super(session);
		this.realmClient = realmClient;
	}

	@Override
	protected UserClientRoles newObject() {
		return new UserClientRoles();
	}

	@Override
	protected CoreAbstLoader<UserClientRoles> getLoader() {
		return this;
	}

	@Override
	protected Object getIdentif(UserClientRoles t) {
		return t;
	}
	@Override
	protected UserClientRoles lookup(Object identif) {
		UserClientRoles id = (UserClientRoles) identif;
		UserReprestn userReprestn = realmClient.findUser(id.getRealmId(), id.getUsername());
		if(userReprestn==null) return null;
		return realmClient.findUserClientRole(id);
	}
	@Override
	protected UserClientRoles update(UserClientRoles t) {
		return t;
	}
	@Override
	protected UserClientRoles create(UserClientRoles t) {
		return realmClient.addClientRole(t);
	}
}
