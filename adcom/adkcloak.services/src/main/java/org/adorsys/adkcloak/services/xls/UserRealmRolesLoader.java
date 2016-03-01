package org.adorsys.adkcloak.services.xls;

import org.adorsys.adcore.xls.CoreAbstLoader;
import org.keycloak.models.KeycloakSession;

public class UserRealmRolesLoader extends CoreAbstModelLoader<UserRealmRoles> {

	private RealmClient realmClient;

	public UserRealmRolesLoader(KeycloakSession session,RealmClient realmClient) {
		super(session);
		this.realmClient = realmClient;
	}

	@Override
	protected UserRealmRoles newObject() {
		return new UserRealmRoles();
	}

	@Override
	protected CoreAbstLoader<UserRealmRoles> getLoader() {
		return this;
	}

	@Override
	protected Object getIdentif(UserRealmRoles t) {
		return t;
	}
	@Override
	protected UserRealmRoles lookup(Object identif) {
		UserRealmRoles id = (UserRealmRoles) identif;
		UserReprestn userReprestn = realmClient.findUser(id.getRealmId(), id.getUsername());
		if(userReprestn==null) return null;
		return realmClient.findUserRealmRole(id);
	}
	@Override
	protected UserRealmRoles update(UserRealmRoles t) {
		return t;
	}
	@Override
	protected UserRealmRoles create(UserRealmRoles t) {
		return realmClient.addRealmRole(t);
	}
}
