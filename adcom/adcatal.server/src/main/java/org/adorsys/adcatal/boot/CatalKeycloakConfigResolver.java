package org.adorsys.adcatal.boot;

import org.adorsys.adkcloak.adapter.file.FileBasedKeycloakConfigResolver;

public class CatalKeycloakConfigResolver extends FileBasedKeycloakConfigResolver {

	@Override
	protected String getResource() {
		return "adcatal.server";
	}
}
