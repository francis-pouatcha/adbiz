package org.adorsys.adkcloak.services.managers;

import org.adorsys.adkcloak.services.xls.KcloakXlsLoader;
import org.adorsys.adkcloak.services.xls.RealmClient;
import org.jboss.logging.Logger;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class InitialXlsLoad {
    private static final Logger logger = Logger.getLogger(InitialXlsLoad.class);

    public static boolean loadApplicationRealm(KeycloakSessionFactory sessionFactory, String contextPath) {
        RealmClient realmClient = new RealmClient(sessionFactory, contextPath);
        KeycloakSession session = sessionFactory.create();
        KcloakXlsLoader loader = new KcloakXlsLoader(session,realmClient);
		try {
			loader.getDataSheetLoader().process();
			return true;
		} catch (Exception e) {
			logger.error("Can not load data sheet", e);
			return false;
		} finally {
            session.close();
		}
    }
}
