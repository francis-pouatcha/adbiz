package org.adorsys.adkcloak.adapter.properties;

import java.io.InputStream;
import java.util.logging.Logger;

import org.adorsys.adcore.env.EnvProperties;
import org.adorsys.adcore.env.PropertiesHolder;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.representations.adapters.config.AdapterConfig;

public abstract class PropertiesBasedKeycloakConfigResolver implements KeycloakConfigResolver {
	private static Logger LOG = Logger.getLogger(PropertiesBasedKeycloakConfigResolver.class.getSimpleName());

	private KeycloakDeployment deployment;
	
	public void initDeployment() {
		String proertyFileName = EnvProperties.getEnvOrSystPropThrowException("IDP_CLIENT_PROPERTIES_FILE");
		PropertiesHolder propertiesHolder = PropertiesHolder.singleton(proertyFileName);
		// Form the credential property dynamically from clientId+".credential"
		EnvProperties envProperties = new EnvProperties(propertiesHolder.toProperties());
		InputStream is = loadConfig();
		AdapterConfig adapterConfig = PropertiesKeycloakDeploymentBuilder.loadAdapterConfig(is, envProperties);
		deployment = KeycloakDeploymentBuilder.build(adapterConfig);
		LOG.fine("Deployment initialized: " + deployment);
	} 

	@Override
	public KeycloakDeployment resolve(HttpFacade.Request request) {
		if (deployment == null)
			initDeployment();
		return deployment;
	}

	protected InputStream loadConfig()
	{
		// load the template. Attention, getClass returns the subclass that is located in the project war. Not this class. keycloak.json musst be in the project war.
		InputStream is = getClass().getResourceAsStream("/keycloak.json");
		if (is == null)
			throw new IllegalStateException("Not able to find the file /keycloak.json. Position the file the project sources under src/main/resources");
		return is;
	}
}
