package org.adorsys.adkcloak.cache.none;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.cache.CacheUserProvider;
import org.keycloak.models.cache.CacheUserProviderFactory;

public class NoneCacheUserProviderFactory implements CacheUserProviderFactory {

    @Override
    public CacheUserProvider create(KeycloakSession session) {
        return new NoneCacheUserProvider(session);
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
        return "none";
    }
}
