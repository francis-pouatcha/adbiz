package org.adorsys.adkeycloak.model.b2.repo;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.adorsys.adcore.b2.jpa.NnV;
import org.adorsys.adkeycloak.model.b2.adapters.B2UserProvider;
import org.adorsys.adkeycloak.model.b2.jpa.B2FederatedIdentityEntity;

public class FederatedIdentityStore extends B2AbstractUserStore<B2FederatedIdentityEntity> {

	public FederatedIdentityStore(B2UserProvider userProvider, EntityManager em, int cacheSize) {
		super(userProvider, em, cacheSize);
	}

	@Override
	protected Class<B2FederatedIdentityEntity> getEntityClass() {
		return B2FederatedIdentityEntity.class;
	}

	public Long countByRealmIdAndUserId(String realmId, String userId){
		if(realmId==null || userId==null) return 0l;
		NnV realmIdNV = NnV.inst(B2FederatedIdentityEntity.getRealmdField(), realmId);
		NnV userIdNV = NnV.inst(B2FederatedIdentityEntity.getUserIdField(), userId);
		return count(entTypeNV, realmIdNV, userIdNV);
	}
	public List<B2FederatedIdentityEntity> findByRealmIdAndUserId(String realmId, String userId, int start, int max){
		if(realmId==null || userId==null) return Collections.emptyList();
		NnV realmIdNV = NnV.inst(B2FederatedIdentityEntity.getRealmdField(), realmId);
		NnV userIdNV = NnV.inst(B2FederatedIdentityEntity.getUserIdField(), userId);
		return find(0,1,entTypeNV, realmIdNV, userIdNV);
	}

	public B2FederatedIdentityEntity findByRealmIdAndUserIdAndIdp(String realmId, String userId, String identityProvider) {
		if(realmId==null || userId==null || identityProvider==null) return null;
		NnV realmIdNV = NnV.inst(B2FederatedIdentityEntity.getRealmdField(), realmId);
		NnV userIdNV = NnV.inst(B2FederatedIdentityEntity.getUserIdField(), userId);
		NnV idpNV = NnV.inst(B2FederatedIdentityEntity.getIdentityProviderField(), identityProvider);
		List<B2FederatedIdentityEntity> entities = find(0,1,entTypeNV, realmIdNV, userIdNV, idpNV);
		if(entities.isEmpty()) return null;
		return entities.iterator().next();
	}

	@Override
	protected String getEntityType() {
		return B2FederatedIdentityEntity.ENT_TYPE;
	}
}
