package org.adorsys.adkcloak.events.b2;

import javax.persistence.EntityManager;

import org.keycloak.Config;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.events.EventStoreProvider;
import org.keycloak.events.EventStoreProviderFactory;
import org.keycloak.events.jpa.JpaEventStoreProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

/**
 * 
 * @author fpo
 *
 */
public class B2EventStoreProviderFactory implements EventStoreProviderFactory {

    public static final String ID = "b2";

    @Override
    public EventStoreProvider create(KeycloakSession session) {
        EntityManager em = session.getProvider(JpaConnectionProvider.class, "b2").getEntityManager();
        return new JpaEventStoreProvider(em);
    }

    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return ID;
    }

}
