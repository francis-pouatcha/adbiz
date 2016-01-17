package org.adorsys.adkeycloak.model.b2.repo;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.adorsys.adcore.b2.jpa.NnV;
import org.adorsys.adkeycloak.model.b2.adapters.B2UserProvider;
import org.adorsys.adcore.b2.jpa.B2PersContent_;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserCredentialEntity;

public class UserCredentialStore extends B2AbstractUserStore<B2UserCredentialEntity> {

	public UserCredentialStore(B2UserProvider userProvider, EntityManager em, int cacheSize) {
		super(userProvider, em, cacheSize);
	}

	@Override
	protected Class<B2UserCredentialEntity> getEntityClass() {
		return B2UserCredentialEntity.class;
	}

	@Override
	protected String getEntityType() {
		return B2UserCredentialEntity.ENT_TYPE;
	}

	public Long countByRealmIdAndUserId(String realmId, String userId){
		if(realmId==null || userId==null) return 0L;
		NnV realmIdNV = NnV.inst(B2UserCredentialEntity.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserCredentialEntity.getUserIdField(), userId);
		return count(entTypeNV, realmIdNV, userIdNV);
	}
	public List<B2UserCredentialEntity> findByRealmIdAndUserId(String realmId, String userId, int start, int max){
		if(realmId==null || userId==null) return Collections.emptyList();
		NnV realmIdNV = NnV.inst(B2UserCredentialEntity.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserCredentialEntity.getUserIdField(), userId);
		return find(start, max, entTypeNV, realmIdNV, userIdNV);
	}

	public Long countByRealmIdAndUserIdAndType(String realmId, String userId, String type){
		if(realmId==null || userId==null) return 0L;
		NnV realmIdNV = NnV.inst(B2UserCredentialEntity.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserCredentialEntity.getUserIdField(), userId);
		NnV typeNV = NnV.inst(B2UserCredentialEntity.getTypeField(), type);
		return count(entTypeNV, realmIdNV, userIdNV, typeNV);
	}
	public List<B2UserCredentialEntity> findByRealmIdAndUserIdAndType(String realmId, String userId, String type, int start, int max){
		if(realmId==null || userId==null) return Collections.emptyList();
		NnV realmIdNV = NnV.inst(B2UserCredentialEntity.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserCredentialEntity.getUserIdField(), userId);
		NnV typeNV = NnV.inst(B2UserCredentialEntity.getTypeField(), type);
		return findOrderDesc(start, max, B2PersContent_.valueDt,entTypeNV, realmIdNV, userIdNV, typeNV);
	}

}
