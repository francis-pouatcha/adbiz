package org.adorsys.adkcloak.adapter.properties;

import java.io.IOException;
import java.io.InputStream;

import org.adorsys.adcore.env.EnvProperties;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.keycloak.representations.adapters.config.AdapterConfig;


public class PropertiesKeycloakDeploymentBuilder {

	public static AdapterConfig loadAdapterConfig(InputStream is, EnvProperties envProperties) {
        ObjectMapper mapper = new ObjectMapper(new PropertiesJsonParserFactory(envProperties));
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
        AdapterConfig adapterConfig;
        try {
            adapterConfig = mapper.readValue(is, AdapterConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return adapterConfig;
    }
}
