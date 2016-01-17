package org.adorsys.adkeycloak.model.b2.repo;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.adorsys.adcore.b2.jpa.NnV;
import org.adorsys.adkeycloak.model.b2.adapters.B2UserProvider;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserRequiredActionEntity;

public class UserRequiredActionStore extends B2AbstractUserStore<B2UserRequiredActionEntity> {

	public UserRequiredActionStore(B2UserProvider userProvider, EntityManager em, int cacheSize) {
		super(userProvider, em, cacheSize);
	}

	@Override
	protected Class<B2UserRequiredActionEntity> getEntityClass() {
		return B2UserRequiredActionEntity.class;
	}

	public Long countByRealmIdAndUserIdAndName(String realmId, String userId, String attrName){
		if(realmId==null || attrName==null) return 0L;
		NnV realmIdNV = NnV.inst(B2UserRequiredActionEntity.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserRequiredActionEntity.getUserIdField(), userId);
		NnV nameNV = NnV.inst(B2UserRequiredActionEntity.getNameField(), attrName);
		return count(entTypeNV, realmIdNV, userIdNV, nameNV);
	}
	public List<B2UserRequiredActionEntity> findByRealmIdAndUserIdAndName(String realmId, String userId, String attrName, int start, int max){
		if(realmId==null || attrName==null) return Collections.emptyList();
		NnV realmIdNV = NnV.inst(B2UserRequiredActionEntity.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserRequiredActionEntity.getUserIdField(), userId);
		NnV nameNV = NnV.inst(B2UserRequiredActionEntity.getNameField(), attrName);
		return find(start, max, entTypeNV, realmIdNV, userIdNV, nameNV);
	}

	public Long countByRealmIdAndUserId(String realmId, String userId){
		if(realmId==null) return 0L;
		NnV realmIdNV = NnV.inst(B2UserRequiredActionEntity.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserRequiredActionEntity.getUserIdField(), userId);
		return count(entTypeNV, realmIdNV, userIdNV);
	}
	public List<String> findNameByRealmIdAndUserId(String realmId, String userId, int start, int max){
		if(realmId==null) return Collections.<String>emptyList();
		NnV realmIdNV = NnV.inst(B2UserRequiredActionEntity.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserRequiredActionEntity.getUserIdField(), userId);
		return find(B2UserRequiredActionEntity.getNameField(),start, max, entTypeNV, realmIdNV, userIdNV);
	}
	public List<B2UserRequiredActionEntity> findByRealmIdAndUserId(String realmId, String userId, int start, int max){
		if(realmId==null) return Collections.<B2UserRequiredActionEntity>emptyList();
		NnV realmIdNV = NnV.inst(B2UserRequiredActionEntity.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserRequiredActionEntity.getUserIdField(), userId);
		return find(start, max, entTypeNV, realmIdNV, userIdNV);
	}

	@Override
	protected String getEntityType() {
		return B2UserRequiredActionEntity.ENT_TYPE;
	}

	
}
