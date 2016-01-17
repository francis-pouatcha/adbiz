package org.adorsys.adkeycloak.model.b2.adapters;

import javax.persistence.EntityManager;

import org.keycloak.Config;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.session.UserSessionPersisterProvider;
import org.keycloak.models.session.UserSessionPersisterProviderFactory;

/**
 * B2UserProviderFactory implementation based on B2 and JPA Connection
 *
 * @author fpo
 */
public class B2UserSessionPersisterProviderFactory implements UserSessionPersisterProviderFactory {

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
    public UserSessionPersisterProvider create(KeycloakSession session) {
        EntityManager em = session.getProvider(JpaConnectionProvider.class, "b2").getEntityManager();
        return new B2UserSessionPersisterProvider(session, em);
    }

    @Override
    public void close() {
    }

}

