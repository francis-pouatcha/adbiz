package org.keycloak.models.sessions.jpa.entities;

import org.keycloak.models.UserSessionModel;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
@Entity
@Table(name = "KC_USER_SESSION")
@NamedQueries({
        @NamedQuery(name = "getUserSessionByUser", query = "select s from UserSessionEntity s where s.realmId = :realmId and s.userId = :userId order by s.started, s.id"),
        @NamedQuery(name = "getUserSessionByBrokerSessionId", query = "select s from UserSessionEntity s where s.realmId = :realmId and s.brokerSessionId = :brokerSessionId order by s.started, s.id"),
        @NamedQuery(name = "getUserSessionByBrokerUserId", query = "select s from UserSessionEntity s where s.realmId = :realmId and s.brokerUserId = :brokerUserId order by s.started, s.id"),
        @NamedQuery(name = "getUserSessionByClient", query = "select s from UserSessionEntity s join s.clientSessions c where s.realmId = :realmId and c.clientId = :clientId order by s.started, s.id"),
        @NamedQuery(name = "getActiveUserSessionByClient", query = "select count(s) from UserSessionEntity s join s.clientSessions c where s.realmId = :realmId and c.clientId = :clientId"),
        @NamedQuery(name = "removeUserSessionByRealm", query = "delete from UserSessionEntity s where s.realmId = :realmId"),
        @NamedQuery(name = "removeUserSessionByUser", query = "delete from UserSessionEntity s where s.realmId = :realmId and s.userId = :userId"),
        @NamedQuery(name = "removeUserSessionByExpired", query = "delete from UserSessionEntity s where s.realmId = :realmId and (s.started < :maxTime or s.lastSessionRefresh < :idleTime)")
})
public class UserSessionEntity {

    @Id
    @Column(name="ID",length = 36)
    protected String id;

    @Column(name="USER_ID")
    protected String userId;

    @Column(name="BROKER_SESSION_ID")
    protected String brokerSessionId;

    @Column(name="BROKER_USER_ID")
    protected String brokerUserId;

    @Column(name="LOGIN_USERNAME")
    protected String loginUsername;

    @Column(name="REALM_ID")
    protected String realmId;

    @Column(name="IP_ADDRESS")
    protected String ipAddress;

    @Column(name="AUTH_METHOD")
    protected String authMethod;

    @Column(name="REMEMBER_ME")
    protected boolean rememberMe;

    @Column(name="STARTED")
    protected int started;

    @Column(name="LAST_SESSION_REFRESH")
    protected int lastSessionRefresh;

    @Column(name="USER_SESSION_STATE")
    protected UserSessionModel.State state;

    @OneToMany(mappedBy="session")
    protected Collection<ClientSessionEntity> clientSessions = new ArrayList<ClientSessionEntity>();

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy="userSession")
    protected Collection<UserSessionNoteEntity> notes = new ArrayList<UserSessionNoteEntity>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getRealmId() {
        return realmId;
    }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
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

    public int getLastSessionRefresh() {
        return lastSessionRefresh;
    }

    public void setLastSessionRefresh(int lastSessionRefresh) {
        this.lastSessionRefresh = lastSessionRefresh;
    }

    public Collection<ClientSessionEntity> getClientSessions() {
        return clientSessions;
    }

     public UserSessionModel.State getState() {
        return state;
    }

    public void setState(UserSessionModel.State state) {
        this.state = state;
    }

    public Collection<UserSessionNoteEntity> getNotes() {
        return notes;
    }

    public void setNotes(Collection<UserSessionNoteEntity> notes) {
        this.notes = notes;
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
}
