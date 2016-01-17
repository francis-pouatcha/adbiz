package org.adorsys.adkcloak.services.xls;

import org.adorsys.adcore.xls.CoreAbstLoader;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.spi.Failure;
import org.keycloak.models.KeycloakSession;
import org.keycloak.representations.idm.RoleRepresentation;

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
			RoleReprestn compositeRoleReprestn = null;
			RoleRepresentation childRole = null;
			if(StringUtils.isNotBlank(rolesRep.getCompositeRoleClientId())){
				compositeRoleReprestn = realmClient.findClientRole(rolesRep.getRealmId(), rolesRep.getCompositeRoleClientId(), rolesRep.getCompositeRoleName());
				if(StringUtils.isBlank(rolesRep.getChildRoleClientId())) throw new IllegalStateException("missing Child role id: " +rolesRep);
				childRole = realmClient.findClientRoleComposite(rolesRep.getRealmId(), compositeRoleReprestn.getId(), rolesRep.getChildRoleClientId(), rolesRep.getChildRoleName());
			} else {
				compositeRoleReprestn = realmClient.findRealmRole(rolesRep.getRealmId(), rolesRep.getCompositeRoleName());				
				childRole =  realmClient.findRealmRoleComposite(rolesRep.getRealmId(), compositeRoleReprestn.getId(), rolesRep.getChildRoleName());
			}
			if(childRole!=null){
				RolesReprestn res = new RolesReprestn();
				res.setChildRoleClientId(rolesRep.getChildRoleClientId());
				res.setChildRoleName(rolesRep.getChildRoleName());
				res.setCompositeRoleClientId(rolesRep.getCompositeRoleClientId());
				res.setCompositeRoleName(rolesRep.getCompositeRoleName());
				res.setRealmId(rolesRep.getRealmId());
				res.setChildRoleId(childRole.getId());
				return res;
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
			RoleRepresentation compositeRoleReprestn = null;
//			if(StringUtils.isNotBlank(rolesRep.getCompositeRoleClientId())){
//				compositeRoleReprestn = realmClient.findClientRole(rolesRep.getRealmId(), rolesRep.getCompositeRoleClientId(), rolesRep.getCompositeRoleName());
//			} else {
//				compositeRoleReprestn = realmClient.findRealmRole(rolesRep.getRealmId(), rolesRep.getCompositeRoleName());				
//			}
			RoleRepresentation childRole = null;
			if(StringUtils.isNotBlank(rolesRep.getCompositeRoleClientId())){
				compositeRoleReprestn = realmClient.findClientRole(rolesRep.getRealmId(), rolesRep.getCompositeRoleClientId(), rolesRep.getCompositeRoleName());
			} else {
				compositeRoleReprestn = realmClient.findRealmRole(rolesRep.getRealmId(), rolesRep.getCompositeRoleName());				
			}

			if(StringUtils.isNotBlank(rolesRep.getChildRoleClientId())) {
				childRole = realmClient.findClientRole(rolesRep.getRealmId(), rolesRep.getChildRoleClientId(), rolesRep.getChildRoleName());
			} else {
				childRole =  realmClient.findRealmRole(rolesRep.getRealmId(), rolesRep.getChildRoleName());
			}
			realmClient.addComposite(rolesRep.getRealmId(), compositeRoleReprestn.getId(), childRole);
			return rolesRep;
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
}
