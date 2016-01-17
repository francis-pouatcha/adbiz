package org.adorsys.adkeycloak.model.b2.jpa;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adcore.b2.jpa.B2PersContent;
import org.adorsys.adcore.b2.jpa.B2PersContent_;
import org.keycloak.models.entities.CredentialEntity;

public class B2UserCredentialEntity extends CredentialEntity implements B2Entity {
	public static final String ENT_TYPE = "userCredentials";

	private int version;
	
	private String realmId;
	
	private String userId;

	@Override
	public int getVersion() {
		return version;
	}

	/**
	 * The container is either the client of the realm.
	 */
	@Override
	public String getCtnrId() {
		return getUserId();
	}
	public static SingularAttribute<B2PersContent, String> getUserIdField(){
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
		return getType();
	}
	public static SingularAttribute<B2PersContent, String> getTypeField(){
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
	
	
//    public void afterRemove() {
//    @NamedQuery(name="credentialByUserAndType", query="select cred from CredentialEntity cred where cred.user = :user and cred.type = :type"),
//    @NamedQuery(name="deleteCredentialsByRealm", query="delete from CredentialEntity cred where cred.user IN (select u from UserEntity u where u.realmId=:realmId)"),
//    @NamedQuery(name="deleteCredentialsByRealmAndLink", query="delete from CredentialEntity cred where cred.user IN (select u from UserEntity u where u.realmId=:realmId and u.federationLink=:link)")
//    }
}
