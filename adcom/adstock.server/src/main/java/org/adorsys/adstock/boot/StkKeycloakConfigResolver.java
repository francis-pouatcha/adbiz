package org.adorsys.adstock.boot;

import org.adorsys.adkcloak.adapter.file.FileBasedKeycloakConfigResolver;

public class StkKeycloakConfigResolver extends FileBasedKeycloakConfigResolver {

	@Override
	protected String getResource() {
		return "adstock.server";
	}
}
