package org.adorsys.adkeycloak.model.b2.jpa;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adcore.b2.jpa.B2PersContent;
import org.adorsys.adcore.b2.jpa.B2PersContent_;
import org.keycloak.models.entities.GroupEntity;

public class B2GroupEntity extends GroupEntity implements B2Entity {
	public static final String ENT_TYPE = "groups";

	private int version;
	
	@Override
	public int getVersion() {
		return version;
	}

	@Override
	public String getCtnrId() {
		return getParentId();
	}
	public static SingularAttribute<B2PersContent, String> getParentIdField(){
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
		return null;
	}

	@Override
	public String getIdx3() {
		return null;
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

	@Override
	public String getRootId() {
		return getRealmId();
	}
	public static SingularAttribute<B2PersContent, String> getRealmIdField(){
		return B2PersContent_.rootId;
	} 
}
