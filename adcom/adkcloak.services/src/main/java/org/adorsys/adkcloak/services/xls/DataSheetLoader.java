package org.adorsys.adkcloak.services.xls;

import org.adorsys.adcore.xls.AbstractLoader;

public class DataSheetLoader extends AbstractLoader {

	@Override
	public String getDir() {
		return System.getProperty("jboss.data.dir", "standalone/data");
	}

	@Override
	public String getSuffix() {
		return ".keycloak.xls";
	}
}
