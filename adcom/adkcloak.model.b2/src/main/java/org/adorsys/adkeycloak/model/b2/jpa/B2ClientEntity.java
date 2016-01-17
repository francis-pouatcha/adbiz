package org.adorsys.adkeycloak.model.b2.jpa;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adcore.b2.jpa.B2PersContent;
import org.adorsys.adcore.b2.jpa.B2PersContent_;
import org.keycloak.models.entities.ClientEntity;

public class B2ClientEntity extends ClientEntity implements B2Entity {
	public static final String ENT_TYPE = "clients";

	private int version;

	@Override
	public int getVersion() {
		return version;
	}

	/**
	 * The client always exists in the context of a realm.
	 */
	@Override
	public String getCtnrId() {
		return getId();
	}

	@Override
	public String getEntType() {
		return ENT_TYPE;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String getGuardId() {
		return null;
	}

	@Override
	public String getRootId() {
		return getRealmId();
	}
	public static SingularAttribute<B2PersContent, String> getRealmIdField(){
		return B2PersContent_.rootId;
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
		return getClientId();
	}
	public static SingularAttribute<B2PersContent, String> getClientIdField(){
		return B2PersContent_.idx2;
	} 

	@Override
	public String getIdx3() {
		return getClientTemplate();
	}
	public static SingularAttribute<B2PersContent, String> getClientTemplateField(){
		return B2PersContent_.idx3;
	} 

	@Override
	public String getIdx4() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIdx5() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIdx6() {
		// TODO Auto-generated method stub
		return null;
	}


    // Remove all roles, which belongs to this application (clientId)
}
