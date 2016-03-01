package org.adorsys.adkcloak.services.xls;

import org.adorsys.adcore.xls.CoreAbstLoader;
import org.jboss.resteasy.spi.Failure;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RoleModel;

public class RolesLoader extends CoreAbstModelLoader<RolesReprestn> {

	private RealmClient realmClient;

	public RolesLoader(KeycloakSession session,RealmClient realmClient) {
		super(session);
		this.realmClient = realmClient;
	}

	@Override
	protected RolesReprestn newObject() {
		return new RolesReprestn();
	}

	@Override
	protected CoreAbstLoader<RolesReprestn> getLoader() {
		return this;
	}

	@Override
	protected Object getIdentif(RolesReprestn t) {
		return t;
	}
	@Override
	protected RolesReprestn lookup(Object identif) {
		try {
			RolesReprestn rolesRep = (RolesReprestn) identif;
			RoleModel childRole = realmClient.findChildRoleFromComposite(rolesRep);
			if(childRole!=null){
				return rolesRep;
			}
			return null;
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
	
	@Override
	protected RolesReprestn update(RolesReprestn t) {
		return t;
	}
	@Override
	protected RolesReprestn create(RolesReprestn rolesRep) {
		try {
			realmClient.addComposite(rolesRep);
			return rolesRep;
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
}
