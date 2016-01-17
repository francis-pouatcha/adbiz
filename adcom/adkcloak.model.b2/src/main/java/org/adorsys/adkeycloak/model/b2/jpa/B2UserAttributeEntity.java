package org.adorsys.adkeycloak.model.b2.jpa;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adcore.b2.jpa.B2PersContent;
import org.adorsys.adcore.b2.jpa.B2PersContent_;
import org.keycloak.models.entities.AbstractIdentifiableEntity;

public class B2UserAttributeEntity extends AbstractIdentifiableEntity implements B2Entity {
	public static final String ENT_TYPE = "userAttributes";

	private int version;
	private String id;
	private String realmId;
	private String userId;
	private String name;
	private String value;
	
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
		return getName();
	}
	public static SingularAttribute<B2PersContent, String> getNameField(){
		return B2PersContent_.idx1;
	} 
	@Override
	public String getIdx2() {
		return getValue();
	}
	public static SingularAttribute<B2PersContent, String> getValueField(){
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
