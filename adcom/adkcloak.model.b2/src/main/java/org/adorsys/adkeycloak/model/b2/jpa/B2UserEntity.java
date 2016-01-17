package org.adorsys.adkeycloak.model.b2.jpa;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adcore.b2.jpa.B2PersContent;
import org.adorsys.adcore.b2.jpa.B2PersContent_;
import org.keycloak.models.entities.UserEntity;

public class B2UserEntity extends UserEntity implements B2Entity {
	public static final String ENT_TYPE = "users";
	private int version;

//    public void afterRemove() {
        // Remove all consents of this user , B2UserConsentEntity -> "userId"
//    }

	@Override
	public int getVersion() {
		return version;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String getCtnrId() {
		return getRealmId();
	}
	public static SingularAttribute<B2PersContent, String> getRealmIdField(){
		return B2PersContent_.ctnrId;
	} 

	@Override
	public String getRootId() {
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
        return getUsername();
	}
	public static SingularAttribute<B2PersContent, String> getUsernameField(){
		return B2PersContent_.idx1;
	} 
	
	public static final String makeUserNameIndex(String realmId, String username){
		return username != null ? realmId + "//" + username : null;
	}

	@Override
	public String getIdx2() {
        return getEmail();
	}
	public static SingularAttribute<B2PersContent, String> getEmailField(){
		return B2PersContent_.idx2;
	} 
	public static final String makeEmailIndex(String realmId, String email){
		return email != null ? realmId + "//" + email : null;
	}

	@Override
	public String getIdx3() {
		return getServiceAccountClientLink();
	}
	public static SingularAttribute<B2PersContent, String> getServiceAccountClientLinkField(){
		return B2PersContent_.idx3;
	} 

	@Override
	public String getIdx4() {
		return getFirstName();
	}
	public static SingularAttribute<B2PersContent, String> getFirstNameField(){
		return B2PersContent_.idx4;
	} 

	@Override
	public String getIdx5() {
		return getLastName();
	}
	public static SingularAttribute<B2PersContent, String> getLastNameField(){
		return B2PersContent_.idx5;
	} 

	@Override
	public String getIdx6() {
		return getFederationLink();
	}
	public static SingularAttribute<B2PersContent, String> getFederationLinkField(){
		return B2PersContent_.idx6;
	} 
	
	
}
