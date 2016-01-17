package org.adorsys.adkeycloak.model.b2.repo;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.adorsys.adcore.b2.jpa.NnV;
import org.adorsys.adkeycloak.model.b2.adapters.B2UserSessionPersisterProvider;
import org.adorsys.adkeycloak.model.b2.jpa.B2ClientSessionEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserSessionEntity;
import org.adorsys.adkeycloak.model.b2.jpa.OfflineStr;

public class ClientSessionStore extends B2AbstractUserSessionStore<B2ClientSessionEntity> {

	public ClientSessionStore(B2UserSessionPersisterProvider userProvider, EntityManager em, int cacheSize) {
		super(userProvider, em, cacheSize);
	}

	@Override
	protected Class<B2ClientSessionEntity> getEntityClass() {
		return B2ClientSessionEntity.class;
	}
	@Override
	protected String getEntityType() {
		return B2ClientSessionEntity.ENT_TYPE;
	}

	public Long countByUserSessionId(String userSessionId){
		if(userSessionId==null) return 0L;
		NnV userSessionIdIdNV = NnV.inst(B2ClientSessionEntity.getUserSessionIdField(), userSessionId);
		return count(entTypeNV, userSessionIdIdNV);
	}
	public List<B2ClientSessionEntity> findByUserSessionId(String userSessionId, int start, int max){
		if(userSessionId==null) return Collections.emptyList();
		NnV userSessionIdNV = NnV.inst(B2ClientSessionEntity.getUserSessionIdField(), userSessionId);
		return find(start,max,entTypeNV, userSessionIdNV);
	}

	
	public void removeByRealmId(String realmId){
		NnV realmIdNv = NnV.inst(B2ClientSessionEntity.getRealmIdField(), realmId);
		removeBatch(entTypeNV, realmIdNv);
	}

	public void removeByUserSessionIdAndOffline(String userSessionId, boolean offline) {
		NnV userSessionIdNv = NnV.inst(B2ClientSessionEntity.getUserSessionIdField(), userSessionId);
		NnV offlineNv = NnV.inst(B2ClientSessionEntity.getOfflineField(), OfflineStr.offlineToString(offline));
		removeBatch(entTypeNV, userSessionIdNv, offlineNv);
	}

	public void removeByRealmIdClientId(String realmId, String clientId) {
		NnV realmIdNv = NnV.inst(B2ClientSessionEntity.getRealmIdField(), realmId);
		NnV clientIdNv = NnV.inst(B2ClientSessionEntity.getClientIdField(), clientId);
		removeBatch(entTypeNV, realmIdNv, clientIdNv);
	}

	public void removeByRealmIdUserId(String realmId, String userId) {
		NnV realmIdNv = NnV.inst(B2ClientSessionEntity.getRealmIdField(), realmId);
		NnV userIdIdNv = NnV.inst(B2ClientSessionEntity.getUserIdField(), userId);
		removeBatch(entTypeNV, realmIdNv, userIdIdNv);
	}

	public void updateAllTimestamps(int time) {
		updateBatch(B2UserSessionEntity.getLastSessionRefreshField(), B2UserSessionEntity.getLastSessionRefreshAttrValue(time), entTypeNV);
	}
}
