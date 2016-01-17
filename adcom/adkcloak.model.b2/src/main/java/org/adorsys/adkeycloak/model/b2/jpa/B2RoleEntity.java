package org.adorsys.adkeycloak.model.b2.jpa;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adcore.b2.jpa.B2PersContent;
import org.adorsys.adcore.b2.jpa.B2PersContent_;
import org.keycloak.models.entities.RoleEntity;

public class B2RoleEntity extends RoleEntity implements B2Entity {
	public static final String ENT_TYPE = "roles";
	public static final String ROLE_TYPE_CLIENT = "CLIENT";
	public static final String ROLE_TYPE_REALM = "REALM";

	private int version;
	
	private String rootId;
	
	private String ctnrId;
	
	private String roleType;
	
	@Override
	public int getVersion() {
		return version;
	}

	/**
	 * The container is either the client of the realm.
	 */
	@Override
	public String getCtnrId() {
		return ctnrId;
	}
	public static SingularAttribute<B2PersContent, String> getCtnrIdField(){
		return B2PersContent_.ctnrId;
	} 

	@Override
	public String getGuardId() {
		return null;
	}

	@Override
	public String getEntType() {
		return ENT_TYPE;
	}

	@Override
	public String getIdx1() {
		return getName();
	}
	public static SingularAttribute<B2PersContent, String> getNameField(){
		return B2PersContent_.idx1;
	} 

	@Override
	public String getIdx2() {
		return getRoleType();
	}
	public static SingularAttribute<B2PersContent, String> getRoleTypeField(){
		return B2PersContent_.idx2;
	} 

	@Override
	public String getIdx3() {
		return getClientId();
	}
	public static SingularAttribute<B2PersContent, String> getClientIdField(){
		return B2PersContent_.idx3;
	} 

	@Override
	public String getRootId() {
		return rootId;
	}
	public static SingularAttribute<B2PersContent, String> getRootIdField(){
		return B2PersContent_.rootId;
	} 
	
	public void setVersion(int version) {
		this.version = version;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public void setCtnrId(String ctnrId) {
		this.ctnrId = ctnrId;
	}
	
	public boolean isClientRole(){
		return ROLE_TYPE_CLIENT.equals(roleType);
	}
	
	public boolean isRealmRole(){
		return ROLE_TYPE_REALM.equals(roleType);
	}

	@Override
	public String getIdx4() {
		return getRealmId();
	}
	public static SingularAttribute<B2PersContent, String> getRealmIdField(){
		return B2PersContent_.idx4;
	} 

	@Override
	public String getIdx5() {
		return null;
	}

	@Override
	public String getIdx6() {
		return null;
	}
	
//    public void afterRemove() {
    	// delete roles of groups (rolsids)
    	// Remove this scope from all clients, which has it -> delete scope of client (scopeids)

        // Remove defaultRoles from realm (defaultRoles)

        // Remove defaultRoles from application

        // Remove this role from others who has it as composite (compositeRoleIds)
//    }
}
