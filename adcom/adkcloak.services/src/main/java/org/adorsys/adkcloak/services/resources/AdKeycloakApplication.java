 package org.adorsys.adkcloak.services.resources;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.adorsys.adkcloak.services.managers.InitialXlsLoad;
import org.apache.commons.io.FileUtils;
import org.jboss.logging.Logger;
import org.jboss.resteasy.core.Dispatcher;
import org.keycloak.services.resources.KeycloakApplication;

public class AdKeycloakApplication extends KeycloakApplication {
    private static final Logger LOG = Logger.getLogger(AdKeycloakApplication.class);

	public AdKeycloakApplication(@Context ServletContext context, @Context Dispatcher dispatcher) {
		super(context, dispatcher);
		LOG.info("Loading initial application realm...");
		InitialXlsLoad.loadApplicationRealm(sessionFactory, contextPath);

		try {
			FileUtils.write(new File(".config.done"), "Done");
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

		LOG.info("Initial application realm loaded.");
	}

	
}
