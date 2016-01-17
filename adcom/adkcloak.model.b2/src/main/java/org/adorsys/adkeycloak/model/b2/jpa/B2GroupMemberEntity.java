package org.adorsys.adkeycloak.model.b2.jpa;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adcore.b2.jpa.B2PersContent;
import org.adorsys.adcore.b2.jpa.B2PersContent_;
import org.keycloak.models.entities.AbstractIdentifiableEntity;

public class B2GroupMemberEntity extends AbstractIdentifiableEntity implements B2Entity {
	public static final String ENT_TYPE = "groupMembers";
    private String userId;
    private String realmId;
    private String groupId;
    private String username;
	private int version;
	
	@Override
	public int getVersion() {
		return version;
	}
	@Override
	public String getCtnrId() {
		return groupId;
	}
	public static SingularAttribute<B2PersContent, String> getGroupIdField(){
		return B2PersContent_.ctnrId;
	} 
	
	@Override
	public String getRootId() {
		return realmId;
	}
	public static SingularAttribute<B2PersContent, String> getRealmdField(){
		return B2PersContent_.rootId;
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
		return userId;
	}
	public static SingularAttribute<B2PersContent, String> getUserIdField(){
		return B2PersContent_.idx1;
	} 
	@Override
	public String getIdx2() {
		return getUsername();
	}
	public static SingularAttribute<B2PersContent, String> getUsernameField(){
		return B2PersContent_.idx2;
	} 
	@Override
	public String getIdx3() {
		return null;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRealmId() {
		return realmId;
	}
	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
