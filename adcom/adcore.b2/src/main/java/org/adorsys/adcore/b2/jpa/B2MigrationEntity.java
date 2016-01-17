package org.adorsys.adcore.b2.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class B2MigrationEntity {
    public static final String SINGLETON_ID = "SINGLETON";
    @Id
    @Column(name="ID", length = 36)
    private String id;

    @Column(name="VERSION", length = 36)
    protected String version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
