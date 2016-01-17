package org.adorsys.adkeycloak.model.b2.repo;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.adorsys.adcore.b2.jpa.B2PersContent;
import org.adorsys.adcore.b2.jpa.NnV;
import org.adorsys.adkeycloak.model.b2.adapters.B2UserSessionPersisterProvider;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserSessionEntity;
import org.adorsys.adkeycloak.model.b2.jpa.OfflineStr;

public class UserSessionStore extends B2AbstractUserSessionStore<B2UserSessionEntity> {

	public UserSessionStore(B2UserSessionPersisterProvider userProvider, EntityManager em, int cacheSize) {
		super(userProvider, em, cacheSize);
	}

	@Override
	protected Class<B2UserSessionEntity> getEntityClass() {
		return B2UserSessionEntity.class;
	}
	@Override
	protected String getEntityType() {
		return B2UserSessionEntity.ENT_TYPE;
	}

	public Long countByUserId(String userId){
		if(userId==null) return 0L;
		NnV userIdNV = NnV.inst(B2UserSessionEntity.getUserIdField(), userId);
		return count(entTypeNV, userIdNV);
	}
	public List<B2UserSessionEntity> findByUserId(String userId, int start, int max){
		if(userId==null) return Collections.emptyList();
		NnV userIdNV = NnV.inst(B2UserSessionEntity.getUserIdField(), userId);
		return find(start,max,entTypeNV, userIdNV);
	}
	
	public void removeByRealmId(String realmId){
		NnV realmIdNv = NnV.inst(B2UserSessionEntity.getRealmIdField(), realmId);
		removeBatch(entTypeNV, realmIdNv);
	}

	public void removeByUserSessionIdAndOffline(String userSessionId, boolean offline) {
		NnV userSessionIdNv = NnV.inst(B2UserSessionEntity.getUserSessionIdField(), userSessionId);
		NnV offlineNv = NnV.inst(B2UserSessionEntity.getOfflineField(), OfflineStr.offlineToString(offline));
		removeBatch(entTypeNV, userSessionIdNv, offlineNv);
	}

	public int countUserSession(boolean offline) {
		NnV offlineNv = NnV.inst(B2UserSessionEntity.getOfflineField(), OfflineStr.offlineToString(offline));
		return count(entTypeNV, offlineNv).intValue();
	}

	public void updateAllTimestamps(int time) {
		updateBatch(B2UserSessionEntity.getLastSessionRefreshField(), B2UserSessionEntity.getLastSessionRefreshAttrValue(time), entTypeNV);
	}

	@Override
	public B2UserSessionEntity load(String id) {
		B2UserSessionEntity userSessionEntity = super.load(id);
		B2PersContent persContent = loadNative(id);
		userSessionEntity.readLastSessionRefresh(persContent);
		return userSessionEntity;
	}
	
}
