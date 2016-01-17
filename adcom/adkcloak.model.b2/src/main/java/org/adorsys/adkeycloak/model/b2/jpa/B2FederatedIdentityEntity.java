package org.adorsys.adkeycloak.model.b2.jpa;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adcore.b2.jpa.B2PersContent;
import org.keycloak.models.entities.FederatedIdentityEntity;
import org.adorsys.adcore.b2.jpa.B2PersContent_;
import org.keycloak.models.utils.KeycloakModelUtils;

public class B2FederatedIdentityEntity extends FederatedIdentityEntity implements B2Entity {
	
	public static final String ENT_TYPE = "federatedIdentities";
	private String id = KeycloakModelUtils.generateId();
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
		return getIdentityProvider();
	}
	public static SingularAttribute<B2PersContent, String> getIdentityProviderField(){
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
	public void setVersion(int version) {
		this.version = version;
	}
	@Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}
	public String getRealmId() {
		return realmId;
	}
	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}
	
}
