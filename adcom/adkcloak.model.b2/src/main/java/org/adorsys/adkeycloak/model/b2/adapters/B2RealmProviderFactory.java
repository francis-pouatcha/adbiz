package org.adorsys.adkeycloak.model.b2.adapters;

import org.keycloak.Config;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.RealmProviderFactory;

/**
 * Keycloak B2RealmProviderFactory implementation based on B2 and JPA Connection
 *  
 * @author fpo
 */
public class B2RealmProviderFactory implements RealmProviderFactory {

    @Override
    public String getId() {
        return "b2";
    }

    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public RealmProvider create(KeycloakSession session) {
        return new B2RealmProvider(session, session.getProvider(JpaConnectionProvider.class, "b2").getEntityManager());
    }

    @Override
    public void close() {
    }

}

