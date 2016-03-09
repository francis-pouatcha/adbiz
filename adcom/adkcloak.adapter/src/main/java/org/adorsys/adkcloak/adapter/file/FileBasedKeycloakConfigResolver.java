package org.adorsys.adkcloak.adapter.file;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.adorsys.adcore.env.EnvProperties;
import org.adorsys.adcore.env.PropertiesHolder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.representations.adapters.config.AdapterConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Will load the keycloak configuration from a well defined location
 * 
 * @author fpo
 *
 */
public abstract class FileBasedKeycloakConfigResolver implements KeycloakConfigResolver {
	private static Logger LOG = Logger.getLogger(FileBasedKeycloakConfigResolver.class.getSimpleName());
	public static final String AUTH_SERVER_URL_SYSTEM_PROPERTY = "auth-server-url";
	public static final String AUTH_SERVER_URL_ENV_PROPERTY = "AUTH_SERVER_URL";

	private KeycloakDeployment deployment;
	
	public void initDeployment() {
		String proertyFileName = EnvProperties.getEnvOrSystPropThrowException("IDP_CLIENT_PROPERTIES_FILE");
		PropertiesHolder propertiesHolder = PropertiesHolder.singleton(proertyFileName);
		Properties properties = propertiesHolder.toProperties();
		properties.put("CLIENT_ID", getResource());
		properties.put("client.id", getResource());
		// Form the credential property dynamically from clientId+".credential"
		// If this property is set, we also use it in the file.
		String envOrSystPropValueAuthServerUrl = EnvProperties.getEnvOrSystProp(AUTH_SERVER_URL_ENV_PROPERTY);
		if(StringUtils.isBlank(envOrSystPropValueAuthServerUrl)){
			throw new IllegalStateException("Missing property " + AUTH_SERVER_URL_ENV_PROPERTY);
//			envOrSystPropValueAuthServerUrl = getAuthServerUrl(uriInfo);
		}
		properties.put(AUTH_SERVER_URL_SYSTEM_PROPERTY, envOrSystPropValueAuthServerUrl);
		
		EnvProperties envProperties = new EnvProperties(properties);
		String clientConfigFilePath = envProperties.getPropThrowException("IDP_CLIENT_CONFIG_PATH_TEMPLATE");
		String clientConfigContent = null;
		try {
			File inputFile = new File(clientConfigFilePath);
			String defaultClientConfigContentTemplate = FileUtils.readFileToString(inputFile);
			clientConfigContent = envProperties.resolve(defaultClientConfigContentTemplate);
//			File outputFile = File.createTempFile(UUID.randomUUID().toString(), ".json");
//			// TODO optimize
//			FileUtils.write(outputFile, clientConfigContent);
//			return Response.ok(outputFile).header("Content-Disposition", "attachment; filename=" + "keycloak.json").build();
		} catch (IOException e) {
			throw new IllegalStateException("Can not create the client config file at " + clientConfigFilePath);
		}
		
//        InputStream is = null;
//        try {
//            is = new FileInputStream(clientConfigFilePath);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
		ObjectMapper objectMapper = new ObjectMapper();
		AdapterConfig adapterConfig;
		try {
			adapterConfig = objectMapper.reader(AdapterConfig.class).readValue(clientConfigContent);
		} catch (IOException e) {
          throw new RuntimeException(e);
		}
//		AdapterConfig adapterConfig = PropertiesKeycloakDeploymentBuilder.loadAdapterConfig(IOUtils.toInputStream(clientConfigContent), envProperties);
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
	
//	private String getAuthServerUrl(UriInfo uriInfo){
//		URI thisServerUri = uriInfo.getBaseUri();
//		String serverUriString = thisServerUri.toString();
//		if(StringUtils.contains(serverUriString, "://")) throw new IllegalStateException("Not an absolute url : " + serverUriString);
//		String hostAndPort = StringUtils.substringBetween(serverUriString, "://", "/");
//		String scheme = StringUtils.substringBefore(serverUriString, hostAndPort);
//		String mainUrl = scheme + hostAndPort;
//		return mainUrl + "/auth";
//	}
	
}
