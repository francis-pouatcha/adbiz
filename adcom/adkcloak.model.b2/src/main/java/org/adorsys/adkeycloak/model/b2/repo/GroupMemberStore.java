package org.adorsys.adkeycloak.model.b2.repo;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.adorsys.adcore.b2.jpa.NnV;
import org.adorsys.adkeycloak.model.b2.adapters.B2UserProvider;
import org.adorsys.adkeycloak.model.b2.jpa.B2GroupMemberEntity;

public class GroupMemberStore extends B2AbstractUserStore<B2GroupMemberEntity> {

	public GroupMemberStore(B2UserProvider userProvider, EntityManager em, int cacheSize) {
		super(userProvider, em, cacheSize);
	}

	@Override
	protected Class<B2GroupMemberEntity> getEntityClass() {
		return B2GroupMemberEntity.class;
	}

	public Long countByRealmIdAndUserId(String realmId, String userId){
		if(realmId==null || userId==null) return 0L;
		NnV realmIdNV = NnV.inst(B2GroupMemberEntity.getRealmdField(), realmId);
		NnV userIdNV = NnV.inst(B2GroupMemberEntity.getUserIdField(), userId);
		return count(entTypeNV, realmIdNV, userIdNV);
	}
	public List<B2GroupMemberEntity> findByRealmIdAndUserId(String realmId, String userId, int start, int max){
		if(realmId==null || userId==null) return Collections.<B2GroupMemberEntity>emptyList();
		NnV realmIdNV = NnV.inst(B2GroupMemberEntity.getRealmdField(), realmId);
		NnV userIdNV = NnV.inst(B2GroupMemberEntity.getUserIdField(), userId);
		return find(start, max, entTypeNV, realmIdNV, userIdNV);
	}

	public Long countByRealmIdAndGroupId(String realmId, String groupId) {
		if(realmId==null || groupId==null) return 0L;
		NnV realmIdNV = NnV.inst(B2GroupMemberEntity.getRealmdField(), realmId);
		NnV groupIdNV = NnV.inst(B2GroupMemberEntity.getGroupIdField(), groupId);
		return count(entTypeNV, realmIdNV, groupIdNV);
	}
	public List<String> findUserIdsByRealmIdAndGroupIdOrderByUserName(String realmId, String groupId, int firstResult,
			int maxResults) {
		if(realmId==null || groupId==null) return Collections.<String>emptyList();
		NnV realmIdNV = NnV.inst(B2GroupMemberEntity.getRealmdField(), realmId);
		NnV groupIdNV = NnV.inst(B2GroupMemberEntity.getGroupIdField(), groupId);
		return findOrderAsc(B2GroupMemberEntity.getUserIdField(), firstResult, maxResults, B2GroupMemberEntity.getUsernameField(),entTypeNV, realmIdNV, groupIdNV);
	}
	public List<B2GroupMemberEntity> findByRealmIdAndGroupId(String realmId, String groupId, int firstResult, int maxResults) {
		if(realmId==null || groupId==null) return Collections.<B2GroupMemberEntity>emptyList();
		NnV realmIdNV = NnV.inst(B2GroupMemberEntity.getRealmdField(), realmId);
		NnV groupIdNV = NnV.inst(B2GroupMemberEntity.getGroupIdField(), groupId);
		return find(firstResult, maxResults, entTypeNV, realmIdNV, groupIdNV);
	}

	public Long countByRealmIdAndUserIdAndroupId(String realmId, String userId, String groupId){
		if(realmId==null || userId==null || groupId==null) return 0L;
		NnV realmIdNV = NnV.inst(B2GroupMemberEntity.getRealmdField(), realmId);
		NnV userIdNV = NnV.inst(B2GroupMemberEntity.getUserIdField(), userId);
		NnV groupIdNV = NnV.inst(B2GroupMemberEntity.getGroupIdField(), groupId);
		return count(entTypeNV, realmIdNV, userIdNV, groupIdNV);
	}
	public List<B2GroupMemberEntity> findByRealmIdAndUserIdAndGroupId(String realmId, String userId, String groupId,int start, int max){
		if(realmId==null || userId==null) return Collections.<B2GroupMemberEntity>emptyList();
		NnV realmIdNV = NnV.inst(B2GroupMemberEntity.getRealmdField(), realmId);
		NnV userIdNV = NnV.inst(B2GroupMemberEntity.getUserIdField(), userId);
		NnV groupIdNV = NnV.inst(B2GroupMemberEntity.getGroupIdField(), groupId);
		return find(start, max, entTypeNV, realmIdNV, userIdNV, groupIdNV);
	}
	
	@Override
	protected String getEntityType() {
		return B2GroupMemberEntity.ENT_TYPE;
	}
}
