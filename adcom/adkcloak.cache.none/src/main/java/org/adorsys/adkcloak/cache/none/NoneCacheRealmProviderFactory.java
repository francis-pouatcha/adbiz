package org.adorsys.adkcloak.cache.none;

import java.util.concurrent.ConcurrentHashMap;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.cache.CacheRealmProvider;
import org.keycloak.models.cache.CacheRealmProviderFactory;

public class NoneCacheRealmProviderFactory implements CacheRealmProviderFactory {

    protected final ConcurrentHashMap<String, String> realmLookup = new ConcurrentHashMap<>();

    @Override
    public CacheRealmProvider create(KeycloakSession session) {
        return new NoneCacheRealmProvider(session);
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
