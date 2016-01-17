package org.adorsys.adkeycloak.model.b2.jpa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adcore.b2.jpa.B2PersContent;
import org.adorsys.adcore.b2.jpa.B2PersContent_;
import org.codehaus.jackson.annotate.JsonProperty;
import org.keycloak.models.ClientSessionModel;

public class B2ClientSessionEntity implements B2Entity {

	public static final String ENT_TYPE = "clientSess";
	
	private int version;
	
    private String clientSessionId;
    private String clientId;
    private int timestamp;

    private String userSessionId;
    private String realmId;
    private String userId;
    
    @JsonProperty("authMethod")
    private String authMethod;

    @JsonProperty("redirectUri")
    private String redirectUri;

    @JsonProperty("protocolMappers")
    private Set<String> protocolMappers;

    @JsonProperty("roles")
    private Set<String> roles;

    @JsonProperty("notes")
    private Map<String, String> notes;

    @JsonProperty("userSessionNotes")
    private Map<String, String> userSessionNotes;

    @JsonProperty("executionStatus")
    private Map<String, ClientSessionModel.ExecutionStatus> executionStatus = new HashMap<>();

    @JsonProperty("action")
    private String action;

    @JsonProperty("requiredActions")
    private Set<String> requiredActions = new HashSet<>();
    
    private boolean offline;

	public String getAuthMethod() {
		return authMethod;
	}

	public void setAuthMethod(String authMethod) {
		this.authMethod = authMethod;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public Set<String> getProtocolMappers() {
		return protocolMappers;
	}

	public void setProtocolMappers(Set<String> protocolMappers) {
		this.protocolMappers = protocolMappers;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public Map<String, String> getNotes() {
		return notes;
	}

	public void setNotes(Map<String, String> notes) {
		this.notes = notes;
	}

	public Map<String, String> getUserSessionNotes() {
		return userSessionNotes;
	}

	public void setUserSessionNotes(Map<String, String> userSessionNotes) {
		this.userSessionNotes = userSessionNotes;
	}

	public Map<String, ClientSessionModel.ExecutionStatus> getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(Map<String, ClientSessionModel.ExecutionStatus> executionStatus) {
		this.executionStatus = executionStatus;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Set<String> getRequiredActions() {
		return requiredActions;
	}

	public void setRequiredActions(Set<String> requiredActions) {
		this.requiredActions = requiredActions;
	}

	public boolean isOffline() {
		return offline;
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getClientSessionId() {
		return clientSessionId;
	}

	public void setClientSessionId(String clientSessionId) {
		this.clientSessionId = clientSessionId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public String getUserSessionId() {
		return userSessionId;
	}

	public void setUserSessionId(String userSessionId) {
		this.userSessionId = userSessionId;
	}

	public String getRealmId() {
		return realmId;
	}

	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}

	@Override
	public String getId() {
		return getClientSessionId();
	}

	@Override
	public void setId(String id) {
		setClientSessionId(id);
	}

	@Override
	public String getCtnrId() {
		return getUserSessionId();
	}
	public static SingularAttribute<B2PersContent, String> getUserSessionIdField(){
		return B2PersContent_.ctnrId;
	} 

	@Override
	public String getRootId() {
		return getRealmId();
	}
	public static SingularAttribute<B2PersContent, String> getRealmIdField(){
		return B2PersContent_.rootId;
	} 

	@Override
	public String getGuardId() {
		return null;
	}

	@Override
	public String getEntType() {
		return ENT_TYPE;
	}

	@Override
	public String getIdx1() {
		return OfflineStr.offlineToString(isOffline());
	}
	public static SingularAttribute<B2PersContent, String> getOfflineField(){
		return B2PersContent_.idx1;
	} 

	@Override
	public String getIdx2() {
		return getClientId();
	}
	public static SingularAttribute<B2PersContent, String> getClientIdField(){
		return B2PersContent_.idx2;
	} 

	@Override
	public String getIdx3() {
		return getUserId();
	}
	public static SingularAttribute<B2PersContent, String> getUserIdField(){
		return B2PersContent_.idx3;
	} 

	@Override
	public String getIdx4() {
		return null;
	}

	@Override
	public String getIdx5() {
		return null;
	}

	@Override
	public String getIdx6() {
		return null;
	}
}
