package org.adorsys.adkeycloak.model.b2.repo;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.adorsys.adcore.b2.jpa.NnV;
import org.adorsys.adkeycloak.model.b2.adapters.B2UserProvider;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserAttributeEntity;

public class UserAttributeStore extends B2AbstractUserStore<B2UserAttributeEntity> {

	public UserAttributeStore(B2UserProvider userProvider, EntityManager em, int cacheSize) {
		super(userProvider, em, cacheSize);
	}

	@Override
	protected Class<B2UserAttributeEntity> getEntityClass() {
		return B2UserAttributeEntity.class;
	}

	public Long countByRealmIdAndNameAndValue(String realmId, String attrName, String attrValue){
		if(realmId==null || attrName==null) return 0L;
		NnV realmIdNV = NnV.inst(B2UserAttributeEntity.getRealmIdField(), realmId);
		NnV nameNV = NnV.inst(B2UserAttributeEntity.getNameField(), attrName);
		NnV valueNV = NnV.inst(B2UserAttributeEntity.getValueField(), attrValue);
		return count(entTypeNV, realmIdNV, nameNV, valueNV);
	}
	public List<String> findUserIdByRealmIdAndNameAndValue(String realmId, String attrName, String attrValue, int start, int max){
		if(realmId==null || attrName==null) return Collections.emptyList();
		NnV realmIdNV = NnV.inst(B2UserAttributeEntity.getRealmIdField(), realmId);
		NnV nameNV = NnV.inst(B2UserAttributeEntity.getNameField(), attrName);
		NnV valueNV = NnV.inst(B2UserAttributeEntity.getValueField(), attrValue);
		return find(B2UserAttributeEntity.getUserIdField(),start, max, entTypeNV, realmIdNV, nameNV, valueNV);
	}

	public Long countByRealmIdAndUserIdAndNameAndValue(String realmId, String userId, String attrName, String attrValue){
		if(realmId==null || attrName==null) return 0L;
		NnV realmIdNV = NnV.inst(B2UserAttributeEntity.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserAttributeEntity.getUserIdField(), userId);
		NnV nameNV = NnV.inst(B2UserAttributeEntity.getNameField(), attrName);
		NnV valueNV = NnV.inst(B2UserAttributeEntity.getValueField(), attrValue);
		return count(entTypeNV, realmIdNV, userIdNV, nameNV, valueNV);
	}
	public List<B2UserAttributeEntity> findByRealmIdAndUserIdAndNameAndValue(String realmId, String userId, String attrName, String attrValue, int start, int max){
		if(realmId==null || attrName==null) return Collections.emptyList();
		NnV realmIdNV = NnV.inst(B2UserAttributeEntity.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserAttributeEntity.getUserIdField(), userId);
		NnV nameNV = NnV.inst(B2UserAttributeEntity.getNameField(), attrName);
		NnV valueNV = NnV.inst(B2UserAttributeEntity.getValueField(), attrValue);
		return find(start, max, entTypeNV, realmIdNV, userIdNV, nameNV, valueNV);
	}

	public Long countByRealmIdAndUserIdAndName(String realmId, String userId, String attrName){
		if(realmId==null || attrName==null) return 0L;
		NnV realmIdNV = NnV.inst(B2UserAttributeEntity.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserAttributeEntity.getUserIdField(), userId);
		NnV nameNV = NnV.inst(B2UserAttributeEntity.getNameField(), attrName);
		return count(entTypeNV, realmIdNV, userIdNV, nameNV);
	}
	public List<B2UserAttributeEntity> findByRealmIdAndUserIdAndName(String realmId, String userId, String attrName, int start, int max){
		if(realmId==null || attrName==null) return Collections.emptyList();
		NnV realmIdNV = NnV.inst(B2UserAttributeEntity.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserAttributeEntity.getUserIdField(), userId);
		NnV nameNV = NnV.inst(B2UserAttributeEntity.getNameField(), attrName);
		return find(start, max, entTypeNV, realmIdNV, userIdNV, nameNV);
	}
	public List<String> loadValueByRealmIdAndUserIdAndName(String realmId, String userId, String attrName, int start, int max){
		if(realmId==null || attrName==null) return Collections.emptyList();
		NnV realmIdNV = NnV.inst(B2UserAttributeEntity.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserAttributeEntity.getUserIdField(), userId);
		NnV nameNV = NnV.inst(B2UserAttributeEntity.getNameField(), attrName);
		return find(B2UserAttributeEntity.getValueField(),start, max, entTypeNV, realmIdNV, userIdNV, nameNV);
	}
	
	public Long countByRealmIdAndUserId(String realmId, String userId){
		if(realmId==null) return 0L;
		NnV realmIdNV = NnV.inst(B2UserAttributeEntity.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserAttributeEntity.getUserIdField(), userId);
		return count(entTypeNV, realmIdNV, userIdNV);
	}
	public List<B2UserAttributeEntity> findByRealmIdAndUserId(String realmId, String userId, int start, int max){
		if(realmId==null) return Collections.<B2UserAttributeEntity>emptyList();
		NnV realmIdNV = NnV.inst(B2UserAttributeEntity.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserAttributeEntity.getUserIdField(), userId);
		return find(start, max, entTypeNV, realmIdNV, userIdNV);
	}
	
	
//    @NamedQuery(name="getAttributesByNameAndValue", query="select attr from UserAttributeEntity attr where attr.name = :name and attr.value = :value"),
//    @NamedQuery(name="deleteUserAttributesByRealm", query="delete from  UserAttributeEntity attr where attr.user IN (select u from UserEntity u where u.realmId=:realmId)"),
//    @NamedQuery(name="deleteUserAttributesByRealmAndLink", query="delete from  UserAttributeEntity attr where attr.user IN (select u from UserEntity u where u.realmId=:realmId and u.federationLink=:link)")

	@Override
	protected String getEntityType() {
		return B2UserAttributeEntity.ENT_TYPE;
	}
}
