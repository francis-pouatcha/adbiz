package org.adorsys.adkcloak.adapter.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import org.adorsys.adcore.env.EnvProperties;
import org.adorsys.adcore.env.PropertiesHolder;
import org.adorsys.adkcloak.adapter.properties.PropertiesKeycloakDeploymentBuilder;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.representations.adapters.config.AdapterConfig;

/**
 * Will load the keycloak configuration from a well defined location
 * 
 * @author fpo
 *
 */
public abstract class FileBasedKeycloakConfigResolver implements KeycloakConfigResolver {
	private static Logger LOG = Logger.getLogger(FileBasedKeycloakConfigResolver.class.getSimpleName());

	private KeycloakDeployment deployment;
	
	public void initDeployment() {
		String proertyFileName = EnvProperties.getEnvOrSystPropThrowException("IDP_CLIENT_PROPERTIES_FILE");
		PropertiesHolder propertiesHolder = PropertiesHolder.singleton(proertyFileName);
		Properties properties = propertiesHolder.toProperties();
		properties.put("CLIENT_ID", getResource());
		properties.put("client.id", getResource());
		// Form the credential property dynamically from clientId+".credential"
		EnvProperties envProperties = new EnvProperties(properties);
		String clientConfigFilePath= envProperties.getPropThrowException("IDP_CLIENT_CONFIG_PATH_TEMPLATE");
        InputStream is = null;
        try {
            is = new FileInputStream(clientConfigFilePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
		AdapterConfig adapterConfig = PropertiesKeycloakDeploymentBuilder.loadAdapterConfig(is, envProperties);
		deployment = KeycloakDeploymentBuilder.build(adapterConfig);
		LOG.fine("Deployment initialized: " + deployment);
	} 

	protected abstract String getResource();

	@Override
	public KeycloakDeployment resolve(HttpFacade.Request request) {
		if (deployment == null)
			initDeployment();
		return deployment;
	}
}
