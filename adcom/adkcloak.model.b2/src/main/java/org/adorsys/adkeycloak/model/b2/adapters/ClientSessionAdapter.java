package org.adorsys.adkeycloak.model.b2.adapters;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.adorsys.adkeycloak.model.b2.jpa.B2ClientSessionEntity;
import org.adorsys.adkeycloak.model.b2.repo.ClientSessionStore;
import org.keycloak.models.ClientModel;
import org.keycloak.models.ClientSessionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserModel.RequiredAction;
import org.keycloak.models.UserSessionModel;

public class ClientSessionAdapter extends B2AbstractUserSessionAdapter<B2ClientSessionEntity> implements ClientSessionModel {
    
    private final B2ClientSessionEntity clientSession;
    private final ClientModel client;

    private UserSessionModel userSession;

    public ClientSessionAdapter(KeycloakSession session, UserSessionModel userSession, 
    		ClientModel client, B2ClientSessionEntity clientSession, ClientSessionStore store) {
        super(store);
        this.clientSession = clientSession;
        this.userSession = userSession;
        this.client = client;
    }

	@Override
	public String getId() {
		return clientSession.getId();
	}

	@Override
	public RealmModel getRealm() {
		return userSession.getRealm();
	}

	@Override
	public ClientModel getClient() {
		return client;
	}

	@Override
	public UserSessionModel getUserSession() {
		return userSession;
	}

	@Override
	public void setUserSession(UserSessionModel userSession) {
		this.userSession = userSession;
		updateEntity();
	}

	@Override
	public String getRedirectUri() {
		return clientSession.getRedirectUri();
	}

	@Override
	public void setRedirectUri(String uri) {
		clientSession.setRedirectUri(uri);
		updateEntity();
	}

	@Override
	public int getTimestamp() {
		return clientSession.getTimestamp();
	}

	@Override
	public void setTimestamp(int timestamp) {
		clientSession.setTimestamp(timestamp);
		updateEntity();
	}

	@Override
	public String getAction() {
		return clientSession.getAction();
	}

	@Override
	public void setAction(String action) {
		clientSession.setAction(action);
		updateEntity();
	}

	@Override
	public Set<String> getRoles() {
		if(clientSession.getRoles()==null) return null;
		return Collections.unmodifiableSet(clientSession.getRoles());
	}

	@Override
	public void setRoles(Set<String> roles) {
		clientSession.setRoles(new HashSet<String>(roles));
	}

	@Override
	public Set<String> getProtocolMappers() {
		if(clientSession.getProtocolMappers()==null) return null;
		return Collections.unmodifiableSet(clientSession.getProtocolMappers());
	}

	@Override
	public void setProtocolMappers(Set<String> protocolMappers) {
		clientSession.setProtocolMappers(new HashSet<String>(protocolMappers));
	}

	@Override
	public Map<String, ExecutionStatus> getExecutionStatus() {
		if(clientSession.getExecutionStatus()==null) return null;
		return Collections.unmodifiableMap(clientSession.getExecutionStatus());
	}

	@Override
	public void setExecutionStatus(String authenticator, ExecutionStatus status) {
		if(clientSession.getExecutionStatus()==null)clientSession.setExecutionStatus(new HashMap<String, ExecutionStatus>());
		clientSession.getExecutionStatus().put(authenticator, status);
		updateEntity();
	}

	@Override
	public void clearExecutionStatus() {
		if(clientSession.getExecutionStatus()!=null)clientSession.getExecutionStatus().clear();
		updateEntity();
	}

	@Override
	public UserModel getAuthenticatedUser() {
		return userSession.getUser();
	}

	@Override
	public void setAuthenticatedUser(UserModel user) {
		throw new IllegalStateException("Not supported setAuthenticatedUser");
	}

	@Override
	public String getAuthMethod() {
		return clientSession.getAuthMethod();
	}

	@Override
	public void setAuthMethod(String method) {
		clientSession.setAuthMethod(method);
	}

	@Override
	public String getNote(String name) {
		if(clientSession.getNotes()==null) return null;
		return clientSession.getNotes().get(name);
	}

	@Override
	public void setNote(String name, String value) {
		if(clientSession.getNotes()==null) clientSession.setNotes(new HashMap<String,String>());;
		clientSession.getNotes().put(name, value);
		updateEntity();
	}

	@Override
	public void removeNote(String name) {
		if(clientSession.getNotes()!=null)clientSession.getNotes().remove(name);
		updateEntity();
	}

	@Override
	public Map<String, String> getNotes() {
		if(clientSession.getNotes()==null) return null;
		return Collections.unmodifiableMap(clientSession.getNotes());
	}

	@Override
	public Set<String> getRequiredActions() {
		if(clientSession.getRequiredActions()==null) return null;
		return Collections.unmodifiableSet(clientSession.getRequiredActions());
	}

	@Override
	public void addRequiredAction(String action) {
		if(clientSession.getRequiredActions()==null)clientSession.setRequiredActions(new HashSet<String>());
		clientSession.getRequiredActions().add(action);
		updateEntity();
	}

	@Override
	public void removeRequiredAction(String action) {
		if(clientSession.getRequiredActions()!=null)clientSession.getRequiredActions().remove(action);
		updateEntity();
	}

	@Override
	public void addRequiredAction(RequiredAction action) {
		if(action!=null)
			addRequiredAction(action.name());
	}

	@Override
	public void removeRequiredAction(RequiredAction action) {
		if(action!=null)removeRequiredAction(action.name());
	}

	@Override
	public void setUserSessionNote(String name, String value) {
		userSession.setNote(name, value);
	}

	@Override
	public Map<String, String> getUserSessionNotes() {
		return userSession.getNotes();
	}

	@Override
	public void clearUserSessionNotes() {
		Map<String, String> notes = userSession.getNotes();
		Set<String> keySet = notes.keySet();
		for (String name : keySet) {
			userSession.removeNote(name);
		}
	}

	@Override
	protected B2ClientSessionEntity getEntity() {
		return clientSession;
	}

}
