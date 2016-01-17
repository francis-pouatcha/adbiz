package org.adorsys.adkeycloak.model.b2.jpa;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adcore.b2.jpa.B2PersContent;
import org.adorsys.adcore.b2.jpa.B2PersContent_;
import org.keycloak.models.entities.ClientTemplateEntity;

public class B2ClientTemplateEntity extends ClientTemplateEntity implements B2Entity {
	public static final String ENT_TYPE = "clientTemplates";
	private int version;

	@Override
	public int getVersion() {
		return version;
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
		// TODO Auto-generated method stub
		return null;
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

}
