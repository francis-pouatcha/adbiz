package org.adorsys.adkeycloak.model.b2.adapters;

import javax.persistence.EntityManager;

import org.adorsys.adcore.b2.jpa.B2MigrationEntity;
import org.keycloak.migration.MigrationModel;

/**
 * 
 * @author fpo
 *
 */
public class MigrationModelAdapter implements MigrationModel {
    protected EntityManager em;

    public MigrationModelAdapter(EntityManager em) {
        this.em = em;
    }

    @Override
    public String getStoredVersion() {
        B2MigrationEntity entity = em.find(B2MigrationEntity.class, B2MigrationEntity.SINGLETON_ID);
        if (entity == null) return "1.8.0";
        return entity.getVersion();
    }

    @Override
    public void setStoredVersion(String version) {
        B2MigrationEntity entity = em.find(B2MigrationEntity.class, B2MigrationEntity.SINGLETON_ID);
        if (entity == null) {
            entity = new B2MigrationEntity();
            entity.setId(B2MigrationEntity.SINGLETON_ID);
            entity.setVersion(version);
            em.persist(entity);
        } else {
            entity.setVersion(version);
            em.flush();
        }
    }
}
