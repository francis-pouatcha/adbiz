package org.adorsys.adkcloak.connections.b2;

import javax.persistence.EntityManager;

import org.keycloak.connections.jpa.JpaConnectionProvider;

/**
 * @author fpo
 */
public class B2ConnectionProvider implements JpaConnectionProvider {

    private final EntityManager em;

    public B2ConnectionProvider(EntityManager em) {
        this.em = em;
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void close() {
        em.close();
    }
}
