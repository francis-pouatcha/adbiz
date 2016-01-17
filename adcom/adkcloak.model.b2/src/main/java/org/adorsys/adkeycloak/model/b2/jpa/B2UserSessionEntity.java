package org.adorsys.adkeycloak.model.b2.jpa;

import java.util.Map;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.b2.jpa.B2Entity;
import org.adorsys.adcore.b2.jpa.B2PersContent;
import org.adorsys.adcore.b2.jpa.B2PersContent_;
import org.codehaus.jackson.annotate.JsonProperty;
import org.keycloak.models.UserSessionModel.State;

public class B2UserSessionEntity implements B2Entity {

	public static final String ENT_TYPE = "userSess";
	private int version;
	
    private String userSessionId;
    private String realmId;
    private String userId;
    private int lastSessionRefresh;
	
    @JsonProperty("brokerSessionId")
    private String brokerSessionId;

    @JsonProperty("brokerUserId")
    private String brokerUserId;

    @JsonProperty("ipAddress")
    private String ipAddress;

    @JsonProperty("authMethod")
    private String authMethod;

    @JsonProperty("rememberMe")
    private boolean rememberMe;

    @JsonProperty("started")
    private int started;

    @JsonProperty("notes")
    private Map<String, String> notes;

    @JsonProperty("state")
    private State state;
    
    private boolean offline;
	
	@Override
	public int getVersion() {
		return version;
	}

	@Override
	public String getCtnrId() {
		return getUserId();
	}
	public static SingularAttribute<B2PersContent, String> getUserIdField(){
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
		return LastSessionRefreshStr.toStr(lastSessionRefresh);
	}
	public static SingularAttribute<B2PersContent, String> getLastSessionRefreshField(){
		return B2PersContent_.idx2;
	} 
	public final void readLastSessionRefresh(B2PersContent content){
		this.lastSessionRefresh = LastSessionRefreshStr.toInt(content.getIdx2());
	}
	public static final String getLastSessionRefreshAttrName(){
		return "lastSessionRefresh";
	}
	public static final String getLastSessionRefreshAttrValue(int lastSessionRefresh){
		return LastSessionRefreshStr.toStr(lastSessionRefresh);
	}

	@Override
	public String getIdx3() {
		return null;
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

	public String getBrokerSessionId() {
		return brokerSessionId;
	}

	public void setBrokerSessionId(String brokerSessionId) {
		this.brokerSessionId = brokerSessionId;
	}

	public String getBrokerUserId() {
		return brokerUserId;
	}

	public void setBrokerUserId(String brokerUserId) {
		this.brokerUserId = brokerUserId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getAuthMethod() {
		return authMethod;
	}

	public void setAuthMethod(String authMethod) {
		this.authMethod = authMethod;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public int getStarted() {
		return started;
	}

	public void setStarted(int started) {
		this.started = started;
	}

	public Map<String, String> getNotes() {
		return notes;
	}

	public void setNotes(Map<String, String> notes) {
		this.notes = notes;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isOffline() {
		return offline;
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getLastSessionRefresh() {
		return lastSessionRefresh;
	}

	public void setLastSessionRefresh(int lastSessionRefresh) {
		this.lastSessionRefresh = lastSessionRefresh;
	}

	@Override
	public String getId() {
		return getUserSessionId();
	}
	public static SingularAttribute<B2PersContent, String> getUserSessionIdField(){
		return B2PersContent_.id;
	} 

	@Override
	public void setId(String id) {
		setUserSessionId(id);
	}
}
