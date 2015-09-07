package org.keycloak.models.jpa.entities;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
@Entity
@Table(name="KC_USER_FEDERATION_MAPPER")
public class UserFederationMapperEntity {

    @Id
    @Column(name="ID", length = 36)
    protected String id;

    @Column(name="NAME")
    protected String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FEDERATION_PROVIDER_ID")
    protected UserFederationProviderEntity federationProvider;

    @Column(name = "FEDERATION_MAPPER_TYPE")
    protected String federationMapperType;

    @ElementCollection
    @MapKeyColumn(name="NAME")
    @Column(name="VALUE")
    @CollectionTable(name="KC_USER_FEDERATION_MAPPER_CONFIG", joinColumns={ @JoinColumn(name="USER_FEDERATION_MAPPER_ID") })
    private Map<String, String> config;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REALM_ID")
    private RealmEntity realm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserFederationProviderEntity getFederationProvider() {
        return federationProvider;
    }

    public void setFederationProvider(UserFederationProviderEntity federationProvider) {
        this.federationProvider = federationProvider;
    }

    public String getFederationMapperType() {
        return federationMapperType;
    }

    public void setFederationMapperType(String federationMapperType) {
        this.federationMapperType = federationMapperType;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    public RealmEntity getRealm() {
        return realm;
    }

    public void setRealm(RealmEntity realm) {
        this.realm = realm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserFederationMapperEntity that = (UserFederationMapperEntity) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
