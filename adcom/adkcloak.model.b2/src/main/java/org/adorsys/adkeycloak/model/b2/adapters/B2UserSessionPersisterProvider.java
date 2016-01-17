package org.adorsys.adkeycloak.model.b2.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;

import org.adorsys.adkeycloak.model.b2.jpa.B2ClientSessionEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserSessionEntity;
import org.adorsys.adkeycloak.model.b2.repo.ClientSessionStore;
import org.adorsys.adkeycloak.model.b2.repo.UserSessionStore;
import org.keycloak.models.ClientModel;
import org.keycloak.models.ClientSessionModel;
import org.keycloak.models.ClientSessionModel.ExecutionStatus;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.models.session.UserSessionPersisterProvider;

public class B2UserSessionPersisterProvider implements UserSessionPersisterProvider {
//    private final KeycloakSession session;
	private final UserSessionStore userSessionStore;
	private final ClientSessionStore clientSessionStore;

	private static final int USER_SESSION_CACHE_SIZE = 10000;
	private static final int CLIENT_SESSION_CACHE_SIZE = 40000;
	public B2UserSessionPersisterProvider(KeycloakSession session, EntityManager em) {
//        this.session = session;
        this.userSessionStore = new UserSessionStore(this, em, USER_SESSION_CACHE_SIZE);
        this.clientSessionStore = new ClientSessionStore(this, em, CLIENT_SESSION_CACHE_SIZE);
    }

	@Override
	public void close() {
	}

	@Override
	public void createUserSession(UserSessionModel userSession, boolean offline) {
		B2UserSessionEntity entity = new B2UserSessionEntity();
		entity = updateUserSessionEntity(userSession, entity, offline);
		userSessionStore.save(entity);
	}
	
	private B2UserSessionEntity updateUserSessionEntity(UserSessionModel userSession, B2UserSessionEntity entity, boolean offline){
		entity.setId(userSession.getId());
		entity.setAuthMethod(userSession.getAuthMethod());
		entity.setBrokerSessionId(userSession.getBrokerSessionId());
		entity.setBrokerUserId(userSession.getBrokerUserId());
		
		entity.setId(userSession.getId());
		entity.setIpAddress(userSession.getIpAddress());
		entity.setLastSessionRefresh(userSession.getLastSessionRefresh());
		entity.setNotes(new HashMap<String, String>());
		Map<String, String> userSessionNotes = userSession.getNotes();
		if(userSessionNotes!=null){
			entity.getNotes().putAll(userSessionNotes);
		}
		
		entity.setOffline(offline);
		entity.setRealmId(userSession.getRealm().getId());
		entity.setRememberMe(userSession.isRememberMe());
		entity.setStarted(userSession.getStarted());
		entity.setState(userSession.getState());
		entity.setUserId(userSession.getUser().getId());
		
		return entity;
	}
	
	private void addClientSessions(B2UserSessionEntity entity, ClientSessionModel clientSession, boolean offline){
		String clientSessionId = clientSession.getId();
		B2ClientSessionEntity clientSessionEntity = clientSessionStore.load(clientSessionId);
		if(clientSessionEntity==null) {
			clientSessionEntity = new B2ClientSessionEntity();
		}
		updateClientSessionEntity(clientSession, clientSessionEntity, offline);
		clientSessionStore.save(clientSessionEntity);
	}
	
	private void updateClientSessionEntity(ClientSessionModel clientSession, B2ClientSessionEntity clientSessionEntity, boolean offline) {
		clientSessionEntity.setAction(clientSession.getAction());
		clientSessionEntity.setAuthMethod(clientSession.getAuthMethod());
		clientSessionEntity.setClientId(clientSession.getClient().getId());
		clientSessionEntity.setClientId(clientSession.getClient().getId());
		clientSessionEntity.setClientSessionId(clientSession.getId());
		Map<String, ExecutionStatus> csmES = clientSession.getExecutionStatus();
		Map<String, ExecutionStatus> cseES = new HashMap<String, ExecutionStatus>();
		clientSessionEntity.setExecutionStatus(cseES);
		if(csmES!=null && !cseES.isEmpty()){
			Set<Entry<String,ExecutionStatus>> entrySet = csmES.entrySet();
			for (Entry<String, ExecutionStatus> entry : entrySet) {
				cseES.put(entry.getKey(), entry.getValue());
			}
		}
		Map<String, String> notes = new HashMap<String, String>();
		clientSessionEntity.setNotes(notes);
		Map<String, String> csmNotes = clientSession.getNotes();
		if(csmNotes!=null && !csmNotes.isEmpty()){
			Set<Entry<String, String>> entrySet = csmNotes.entrySet();
			for (Entry<String, String> entry : entrySet) {
				notes.put(entry.getKey(), entry.getValue());
			}
		}
		
		clientSessionEntity.setOffline(offline);
		
		Set<String> protocolMappers = clientSession.getProtocolMappers();
		if(protocolMappers!=null){
			clientSessionEntity.setProtocolMappers(new HashSet<String>(protocolMappers));
		} else {
			clientSessionEntity.setProtocolMappers(new HashSet<String>());
		}
		
		clientSessionEntity.setRedirectUri(clientSession.getRedirectUri());
		
		Set<String> requiredActions = clientSession.getRequiredActions();
		if(requiredActions!=null){
			clientSessionEntity.setRequiredActions(new HashSet<String>(requiredActions));
		} else {
			clientSessionEntity.setRequiredActions(new HashSet<String>());
		}
		
		Set<String> roles = clientSession.getRoles();
		if(roles!=null){
			clientSessionEntity.setRoles(new HashSet<String>(roles));
		} else {
			clientSessionEntity.setRoles(new HashSet<String>());
		}
		
		clientSessionEntity.setTimestamp(clientSession.getTimestamp());
		
		Map<String, String> userSessionNotes = clientSession.getUserSessionNotes();
		if(userSessionNotes!=null){
			clientSessionEntity.setUserSessionNotes(new HashMap<String, String>(userSessionNotes));
		} else {
			clientSessionEntity.setUserSessionNotes(new HashMap<String, String>());
		}
	}

	@Override
	public void createClientSession(ClientSessionModel clientSession, boolean offline) {
		UserSessionModel userSession = clientSession.getUserSession();
		B2UserSessionEntity userSessionEntity = userSessionStore.load(userSession.getId());
		addClientSessions(userSessionEntity, clientSession, offline);
	}

	@Override
	public void updateUserSession(UserSessionModel userSession, boolean offline) {
		B2UserSessionEntity userSessionEntity = userSessionStore.load(userSession.getId());
		updateUserSessionEntity(userSession, userSessionEntity, offline);
		List<ClientSessionModel> clientSessions = userSession.getClientSessions();
		if(clientSessions!=null){
			for (ClientSessionModel clientSession : clientSessions) {
				addClientSessions(userSessionEntity, clientSession, offline);
			}
		}
	}

	@Override
	public void removeUserSession(String userSessionId, boolean offline) {
		userSessionStore.removeByUserSessionIdAndOffline(userSessionId, offline);
		clientSessionStore.removeByUserSessionIdAndOffline(userSessionId, offline);
	}

	@Override
	public void removeClientSession(String clientSessionId, boolean offline) {
		clientSessionStore.remove(clientSessionId);
	}

	@Override
	public void onRealmRemoved(RealmModel realm) {
		userSessionStore.removeByRealmId(realm.getId());
		clientSessionStore.removeByRealmId(realm.getId());
	}

	@Override
	public void onClientRemoved(RealmModel realm, ClientModel client) {
		clientSessionStore.removeByRealmIdClientId(realm.getId(), client.getId());
	}

	@Override
	public void onUserRemoved(RealmModel realm, UserModel user) {
		clientSessionStore.removeByRealmIdUserId(realm.getId(), user.getId());
	}

	@Override
	public void clearDetachedUserSessions() {
		Long userSessionCount = userSessionStore.countAll();
		int processed = 0;
		List<String> candidateIds = new ArrayList<String>();
		while(processed<userSessionCount){
			int start = processed;
			processed+=USER_SESSION_CACHE_SIZE;
			if(candidateIds.size()>CLIENT_SESSION_CACHE_SIZE) break;
			List<String> userSessionIds = userSessionStore.listIds(start, USER_SESSION_CACHE_SIZE);
			for (String userSessionId : userSessionIds) {
				Long count = clientSessionStore.countByUserSessionId(userSessionId);
				if(count<=0){
					candidateIds.add(userSessionId);
					if(candidateIds.size()>CLIENT_SESSION_CACHE_SIZE) break;
				}
			}
		}
		for (String userSessionId : candidateIds) {
			userSessionStore.remove(userSessionId);
		}
//        @NamedQuery(name="deleteDetachedUserSessions", query="delete from PersistentUserSessionEntity sess where sess.userSessionId NOT IN (select c.userSessionId from PersistentClientSessionEntity c)"),
	}

	@Override
	public void updateAllTimestamps(int time) {
		userSessionStore.updateAllTimestamps(time);
		clientSessionStore.updateAllTimestamps(time);
	}

	@Override
	public List<UserSessionModel> loadUserSessions(int firstResult, int maxResults, boolean offline) {
		return null;
	}

	@Override
	public int getUserSessionsCount(boolean offline) {
		return userSessionStore.countUserSession(offline);
	}

	public void removeStrict(String entType, String id) {
		if(entType==null) return;
		if(entType.equals(B2UserSessionEntity.ENT_TYPE)){
			userSessionStore.removeStrict(id);
		} else if (entType.equals(B2ClientSessionEntity.ENT_TYPE)){
			clientSessionStore.removeStrict(id);
		}
	}

	public UserSessionStore getUserSessionStore() {
		return userSessionStore;
	}

	public ClientSessionStore getClientSessionStore() {
		return clientSessionStore;
	}
	
}
