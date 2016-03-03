package org.adorsys.adcom.adres.lib;

import java.io.File;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.adorsys.adcore.env.EnvProperties;
import org.adorsys.adcore.env.PropertiesHolder;

public abstract class KeyCloakConfigEndpoint {

	@GET
	@Produces({ "application/json" })
	public Response getKeyCloakConfig() {
		String proertyFileName = EnvProperties.getEnvOrSystPropThrowException("IDP_CLIENT_PROPERTIES_FILE");
		PropertiesHolder propertiesHolder = PropertiesHolder.singleton(proertyFileName);
		Properties properties = propertiesHolder.toProperties();
		properties.put("CLIENT_ID", getResource());
		properties.put("client.id", getResource());
		// Form the credential property dynamically from clientId+".credential"
		EnvProperties envProperties = new EnvProperties(properties);
		String clientConfigFilePath = envProperties.getPropThrowException("IDP_CLIENT_CONFIG_PATH_TEMPLATE");
		File file = new File(clientConfigFilePath);
		return Response.ok(file).header("Content-Disposition", "attachment; filename=" + "keycloak.json").build();
	}

	protected abstract String getResource();
}
