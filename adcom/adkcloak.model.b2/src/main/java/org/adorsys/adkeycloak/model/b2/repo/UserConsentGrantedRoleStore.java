package org.adorsys.adkeycloak.model.b2.repo;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate.BooleanOperator;

import org.adorsys.adcore.b2.jpa.NnV;
import org.adorsys.adcore.b2.jpa.NnVs;
import org.adorsys.adcore.b2.jpa.Operation;
import org.adorsys.adkeycloak.model.b2.adapters.B2UserProvider;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserConsentGrantedRole;

public class UserConsentGrantedRoleStore extends B2AbstractUserStore<B2UserConsentGrantedRole> {

	public UserConsentGrantedRoleStore(B2UserProvider userProvider, EntityManager em, int cacheSize) {
		super(userProvider, em, cacheSize);
	}

	@Override
	protected Class<B2UserConsentGrantedRole> getEntityClass() {
		return B2UserConsentGrantedRole.class;
	}

	public Long countByRealmIdAndUserId(String realmId, String userId){
		if(realmId==null || userId==null) return 0L;
		NnV realmIdNV = NnV.inst(B2UserConsentGrantedRole.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserConsentGrantedRole.getUserIdField(), userId);
		return count(entTypeNV, realmIdNV, userIdNV);
	}
	public List<B2UserConsentGrantedRole> findByRealmIdAndUserId(String realmId, String userId, int start, int max){
		if(realmId==null || userId==null) return Collections.emptyList();
		NnV realmIdNV = NnV.inst(B2UserConsentGrantedRole.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserConsentGrantedRole.getUserIdField(), userId);
		return find(start, max, entTypeNV, realmIdNV, userIdNV);
	}

	public Long countUserRoles(String realmId, String userId) {
		if(realmId==null || userId==null) return 0L;
		NnV realmIdNV = NnV.inst(B2UserConsentGrantedRole.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserConsentGrantedRole.getUserIdField(), userId);
		NnV useridIsNullNV = NnV.inst(B2UserConsentGrantedRole.getUserIdField(), null, Operation.ISNULL);
		return count(NnVs.inst(entTypeNV, realmIdNV), NnVs.inst(BooleanOperator.OR, userIdNV, useridIsNullNV));
	}
	public List<B2UserConsentGrantedRole> findUserRoles(String realmId, String userId, int firstResult,int maxResults) {
		NnV realmIdNV = NnV.inst(B2UserConsentGrantedRole.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserConsentGrantedRole.getUserIdField(), userId);
		NnV useridIsNullNV = NnV.inst(B2UserConsentGrantedRole.getUserIdField(), null, Operation.ISNULL);
		return find(firstResult, maxResults, NnVs.inst(entTypeNV, realmIdNV), NnVs.inst(BooleanOperator.OR, userIdNV, useridIsNullNV));
	}
	public List<String> findRoleIdsOfUserRoles(String realmId, String userId, int firstResult,int maxResults) {
		NnV realmIdNV = NnV.inst(B2UserConsentGrantedRole.getRealmIdField(), realmId);
		NnV userIdNV = NnV.inst(B2UserConsentGrantedRole.getUserIdField(), userId);
		NnV useridIsNullNV = NnV.inst(B2UserConsentGrantedRole.getUserIdField(), null, Operation.ISNULL);
		return find(B2UserConsentGrantedRole.getRoleIdField(), firstResult, maxResults, NnVs.inst(entTypeNV, realmIdNV), NnVs.inst(BooleanOperator.OR, userIdNV, useridIsNullNV));
	}

	public Long countByRealmIdAndRoleIdAndUserIdIsNull(String realmId, String roleId){
		if(realmId==null || roleId==null) return 0L;
		NnV realmIdNV = NnV.inst(B2UserConsentGrantedRole.getRealmIdField(), realmId);
		NnV roleIdNV = NnV.inst(B2UserConsentGrantedRole.getRoleIdField(), roleId);
		NnV useridIsNullNV = NnV.inst(B2UserConsentGrantedRole.getUserIdField(), null, Operation.ISNULL);
		return count(entTypeNV, realmIdNV, roleIdNV, useridIsNullNV);
	}
	
	@Override
	protected String getEntityType() {
		return B2UserConsentGrantedRole.ENT_TYPE;
	}

	public Long countByRealmIdAndRoleId(String realmId, String roleId){
		if(realmId==null || roleId==null) return 0L;
		NnV realmIdNV = NnV.inst(B2UserConsentGrantedRole.getRealmIdField(), realmId);
		NnV roleIdNV = NnV.inst(B2UserConsentGrantedRole.getRoleIdField(), roleId);
		return count(entTypeNV, realmIdNV, roleIdNV);
	}
	public List<B2UserConsentGrantedRole> findByRealmIdAndRoleId(String realmId, String roleId, int start, int max){
		if(realmId==null || roleId==null) return Collections.emptyList();
		NnV realmIdNV = NnV.inst(B2UserConsentGrantedRole.getRealmIdField(), realmId);
		NnV roleIdNV = NnV.inst(B2UserConsentGrantedRole.getRoleIdField(), roleId);
		return find(start, max, entTypeNV, realmIdNV, roleIdNV);
	}
}
