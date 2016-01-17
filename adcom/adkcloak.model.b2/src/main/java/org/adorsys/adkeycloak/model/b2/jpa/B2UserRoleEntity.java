package org.adorsys.adkeycloak.model.b2.jpa;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adcore.b2.jpa.B2PersContent;
import org.adorsys.adcore.b2.jpa.B2PersContent_;
import org.keycloak.models.entities.AbstractIdentifiableEntity;

public class B2UserRoleEntity extends AbstractIdentifiableEntity implements B2Entity {
	public static final String ENT_TYPE = "userRoles";

	private int version;
	
	private String id; 
	
	private String realmId;
	
	private String userId;
	
	private String roleId;
	
	@Override
	public int getVersion() {
		return version;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getRealmId() {
		return realmId;
	}
	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * The container is either the client of the realm.
	 */
	@Override
	public String getCtnrId() {
		return getRealmId();
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
		return getRoleId();
	}
	public static SingularAttribute<B2PersContent, String> getRoleIdField(){
		return B2PersContent_.idx1;
	} 

	@Override
	public String getIdx2() {
		return getUserId();
	}
	public static SingularAttribute<B2PersContent, String> getUserIdField(){
		return B2PersContent_.idx2;
	} 

	@Override
	public String getIdx3() {
		return null;
	}

	@Override
	public String getRootId() {
		return getRealmId();
	}
	public static SingularAttribute<B2PersContent, String> getRealmIdField(){
		return B2PersContent_.rootId;
	} 
	
	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String getIdx4() {
		return null;
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
