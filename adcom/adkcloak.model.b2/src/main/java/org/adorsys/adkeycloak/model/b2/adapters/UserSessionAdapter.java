package org.adorsys.adkeycloak.model.b2.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adorsys.adkeycloak.model.b2.jpa.B2ClientSessionEntity;
import org.adorsys.adkeycloak.model.b2.jpa.B2UserSessionEntity;
import org.adorsys.adkeycloak.model.b2.repo.UserSessionStore;
import org.keycloak.models.ClientModel;
import org.keycloak.models.ClientSessionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserSessionModel;

public class UserSessionAdapter extends B2AbstractUserSessionAdapter<B2UserSessionEntity> implements UserSessionModel {
    
    private final B2UserSessionEntity userSession;
    private final UserModel user;
    private final RealmModel realm;
    private final KeycloakSession session;

    public UserSessionAdapter(KeycloakSession session, RealmModel realm, UserModel user, B2UserSessionEntity userSession, UserSessionStore store) {
        super(store);
        this.userSession = userSession;
        this.realm = realm;
        this.user = user;
        this.session = session;
    }

	@Override
	public String getId() {
		return userSession.getId();
	}

	@Override
	public RealmModel getRealm() {
		return realm;
	}

	@Override
	public String getBrokerSessionId() {
		return userSession.getBrokerSessionId();
	}

	@Override
	public String getBrokerUserId() {
		return userSession.getBrokerUserId();
	}

	@Override
	public UserModel getUser() {
		return user;
	}

	@Override
	public String getLoginUsername() {
		return user.getUsername();
	}

	@Override
	public String getIpAddress() {
		return userSession.getIpAddress();
	}

	@Override
	public String getAuthMethod() {
		return userSession.getAuthMethod();
	}

	@Override
	public boolean isRememberMe() {
		return userSession.isRememberMe();
	}

	@Override
	public int getStarted() {
		return userSession.getStarted();
	}

	@Override
	public int getLastSessionRefresh() {
		return userSession.getLastSessionRefresh();
	}

	@Override
	public void setLastSessionRefresh(int seconds) {
		userSession.setLastSessionRefresh(seconds);
		updateEntity();
	}

	@Override
	public List<ClientSessionModel> getClientSessions() {
		Long count = getClientSessionStore().countByUserSessionId(userSession.getId());
		List<B2ClientSessionEntity> found = getClientSessionStore().findByUserSessionId(userSession.getId(), 0, count.intValue());
		List<ClientSessionModel> clientSessions = new ArrayList<ClientSessionModel>();
		for (B2ClientSessionEntity clientSessionEntity : found) {
			ClientModel client = realm.getClientById(clientSessionEntity.getClientId());
			clientSessions.add(new ClientSessionAdapter(session, this, client, clientSessionEntity, getClientSessionStore()));
		}
		return clientSessions;
	}

	@Override
	public String getNote(String name) {
		if(userSession.getNotes()==null) return null;
		return userSession.getNotes().get(name);
	}

	@Override
	public void setNote(String name, String value) {
		if(userSession.getNotes()==null) userSession.setNotes(new HashMap<String, String>());
		String oldValue = userSession.getNotes().get(name);
		if(value==oldValue || (value!=null && !value.equals(oldValue))) return;
		if(value==null){
			userSession.getNotes().remove(name);
		} else {
			userSession.getNotes().put(name, value);
		}
		updateEntity();
	}

	@Override
	public void removeNote(String name) {
		String removed = userSession.getNotes().remove(name);
		if(removed!=null) updateEntity();
	}

	@Override
	public Map<String, String> getNotes() {
		return Collections.unmodifiableMap(userSession.getNotes());
	}

	@Override
	public State getState() {
		return userSession.getState();
	}

	@Override
	public void setState(State state) {
		userSession.setState(state);
		updateEntity();
	}

	@Override
	protected B2UserSessionEntity getEntity() {
		return userSession;
	}
}
