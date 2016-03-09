package org.adorsys.adcom.adres.lib;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;
import java.util.UUID;

import javax.swing.plaf.FileChooserUI;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.adorsys.adcore.env.EnvProperties;
import org.adorsys.adcore.env.PropertiesHolder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public abstract class KeyCloakConfigEndpoint {
	
	public static final String AUTH_SERVER_URL_SYSTEM_PROPERTY = "auth-server-url";
	public static final String AUTH_SERVER_URL_ENV_PROPERTY = "AUTH_SERVER_URL";

	@GET
	@Produces({ "application/json" })
	public Response getKeyCloakConfig(@Context UriInfo uriInfo) {
		String proertyFileName = EnvProperties.getEnvOrSystPropThrowException("IDP_CLIENT_PROPERTIES_FILE");
		PropertiesHolder propertiesHolder = PropertiesHolder.singleton(proertyFileName);
		Properties properties = propertiesHolder.toProperties();
		properties.put("CLIENT_ID", getResource());
		properties.put("client.id", getResource());
		
		// If this property is set, we also use it in the file.
		String envOrSystPropValueAuthServerUrl = EnvProperties.getEnvOrSystProp(AUTH_SERVER_URL_ENV_PROPERTY);
		if(StringUtils.isBlank(envOrSystPropValueAuthServerUrl)){
			envOrSystPropValueAuthServerUrl = getAuthServerUrl(uriInfo);
		}
		properties.put(AUTH_SERVER_URL_SYSTEM_PROPERTY, envOrSystPropValueAuthServerUrl);
		
		// Form the credential property dynamically from clientId+".credential"
		EnvProperties envProperties = new EnvProperties(properties);
		String clientConfigFilePath = envProperties.getPropThrowException("IDP_CLIENT_CONFIG_PATH_TEMPLATE");
		File inputFile = new File(clientConfigFilePath);
		
		try {
			String defaultClientConfigContentTemplate = FileUtils.readFileToString(inputFile);
			String clientConfigContent = envProperties.resolve(defaultClientConfigContentTemplate);
			File outputFile = File.createTempFile(UUID.randomUUID().toString(), ".json");
			// TODO optimize
			FileUtils.write(outputFile, clientConfigContent);
			return Response.ok(outputFile).header("Content-Disposition", "attachment; filename=" + "keycloak.json").build();
		} catch (IOException e) {
			throw new IllegalStateException("Can not create the client config file at " + clientConfigFilePath);
		}
		
	}

	private String getAuthServerUrl(UriInfo uriInfo){
		URI thisServerUri = uriInfo.getBaseUri();
		String serverUriString = thisServerUri.toString();
		if(StringUtils.contains(serverUriString, "://")) throw new IllegalStateException("Not an absolute url : " + serverUriString);
		String hostAndPort = StringUtils.substringBetween(serverUriString, "://", "/");
		String scheme = StringUtils.substringBefore(serverUriString, hostAndPort);
		String mainUrl = scheme + hostAndPort;
		return mainUrl + "/auth";
	}
	protected abstract String getResource();
}
