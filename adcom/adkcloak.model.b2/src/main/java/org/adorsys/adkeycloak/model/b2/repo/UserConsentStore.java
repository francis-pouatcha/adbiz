package org.adorsys.adkeycloak.model.b2.repo;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.adorsys.adcore.b2.jpa.NnV;
import org.adorsys.adkeycloak.model.b2.adapters.B2UserProvider;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserConsentEntity;

public class UserConsentStore extends B2AbstractUserStore<B2UserConsentEntity> {

	public UserConsentStore(B2UserProvider userProvider, EntityManager em, int cacheSize) {
		super(userProvider, em, cacheSize);
	}

	@Override
	protected Class<B2UserConsentEntity> getEntityClass() {
		return B2UserConsentEntity.class;
	}
	@Override
	protected String getEntityType() {
		return B2UserConsentEntity.ENT_TYPE;
	}

	public Long countByClientId(String clientId){
		if(clientId==null) return 0L;
		NnV clientIdNV = NnV.inst(B2UserConsentEntity.getClientIdField(), clientId);
		return count(entTypeNV, clientIdNV);
	}
	public List<B2UserConsentEntity> findByClientId(String clientId, int start, int max){
		if(clientId==null) return Collections.emptyList();
		NnV clientIdNV = NnV.inst(B2UserConsentEntity.getClientIdField(), clientId);
		return find(start,max,entTypeNV, clientIdNV);
	}
	
	public Long countByUserId(String userId){
		if(userId==null) return 0L;
		NnV userIdNV = NnV.inst(B2UserConsentEntity.getUserIdField(), userId);
		return count(entTypeNV, userIdNV);
	}
	public List<B2UserConsentEntity> findByUserId(String userId, int start, int max){
		if(userId==null) return Collections.emptyList();
		NnV userIdNV = NnV.inst(B2UserConsentEntity.getUserIdField(), userId);
		return find(start,max,entTypeNV, userIdNV);
	}

	public Long countByUserIdAndClientId(String userId, String clientId){
		if(userId==null || clientId==null) return 0L;
		NnV userIdNV = NnV.inst(B2UserConsentEntity.getUserIdField(), userId);
		NnV clientIdNV = NnV.inst(B2UserConsentEntity.getClientIdField(), clientId);
		return count(entTypeNV, userIdNV, clientIdNV);
	}
	public List<B2UserConsentEntity> findByUserIdAndClientId(String userId, String clientId, int start, int max){
		if(userId==null || clientId==null) return Collections.emptyList();
		NnV userIdNV = NnV.inst(B2UserConsentEntity.getUserIdField(), userId);
		NnV clientIdNV = NnV.inst(B2UserConsentEntity.getClientIdField(), clientId);
		return find(start,max,entTypeNV, userIdNV, clientIdNV);
	}
}
