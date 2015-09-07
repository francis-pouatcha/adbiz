package org.keycloak.services.migration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.keycloak.migration.MigrationProvider;
import org.keycloak.models.ClaimMask;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.utils.ModelToRepresentation;
import org.keycloak.protocol.LoginProtocol;
import org.keycloak.protocol.LoginProtocolFactory;
import org.keycloak.protocol.oidc.OIDCLoginProtocolFactory;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.representations.idm.ProtocolMapperRepresentation;

/**
 * Various common utils needed for migration from older version to newer
 *
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class DefaultMigrationProvider implements MigrationProvider {

    private final KeycloakSession session;

    public DefaultMigrationProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public List<ProtocolMapperRepresentation> getMappersForClaimMask(Long claimMask) {
        Map<String, ProtocolMapperRepresentation> allMappers = getAllDefaultMappers(session);

        if (claimMask == null) {
            return new ArrayList<ProtocolMapperRepresentation>(allMappers.values());
        }

        if (!ClaimMask.hasUsername(claimMask)) {
            allMappers.remove(OIDCLoginProtocolFactory.USERNAME);
        }
        if (!ClaimMask.hasEmail(claimMask)) {
            allMappers.remove(OIDCLoginProtocolFactory.EMAIL);
        }
        if (!ClaimMask.hasName(claimMask)) {
            allMappers.remove(OIDCLoginProtocolFactory.FAMILY_NAME);
            allMappers.remove(OIDCLoginProtocolFactory.FULL_NAME);
            allMappers.remove(OIDCLoginProtocolFactory.GIVEN_NAME);
        }

        return new ArrayList<ProtocolMapperRepresentation>(allMappers.values());
    }

    @Override
    public void close() {
    }

    private static Map<String, ProtocolMapperRepresentation> getAllDefaultMappers(KeycloakSession session) {
        Map<String, ProtocolMapperRepresentation> allMappers = new HashMap<String, ProtocolMapperRepresentation>();

        List<ProviderFactory> loginProtocolFactories = session.getKeycloakSessionFactory().getProviderFactories(LoginProtocol.class);

        for (ProviderFactory factory : loginProtocolFactories) {
            LoginProtocolFactory loginProtocolFactory = (LoginProtocolFactory) factory;
            List<ProtocolMapperModel> currentMappers = loginProtocolFactory.getDefaultBuiltinMappers();

            for (ProtocolMapperModel protocolMapper : currentMappers) {
                ProtocolMapperRepresentation rep = ModelToRepresentation.toRepresentation(protocolMapper);
                allMappers.put(protocolMapper.getName(), rep);
            }
        }

        return allMappers;
    }
}
