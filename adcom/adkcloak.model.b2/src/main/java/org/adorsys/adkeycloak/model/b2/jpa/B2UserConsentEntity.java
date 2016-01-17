package org.adorsys.adkeycloak.model.b2.jpa;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adcore.b2.jpa.B2PersContent;
import org.adorsys.adcore.b2.jpa.B2PersContent_;
import org.keycloak.models.entities.UserConsentEntity;

public class B2UserConsentEntity extends UserConsentEntity implements B2Entity {
	public static final String ENT_TYPE = "userConsents";
	private int version;
	private String realmId;
	
	@Override
	public int getVersion() {
		return version;
	}
	@Override
	public String getCtnrId() {
		return getUserId();
	}
	public static SingularAttribute<B2PersContent, String> getUserIdField(){
		return B2PersContent_.ctnrId;
	} 

	@Override
	public String getRootId() {
		return getRealmId();
	}
	public static SingularAttribute<B2PersContent, String> getRealmIdField(){
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
		return getClientId();
	}
	public static SingularAttribute<B2PersContent, String> getClientIdField(){
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
	public String getRealmId() {
		return realmId;
	}
	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}
}
