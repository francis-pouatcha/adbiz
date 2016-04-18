package org.adorsys.adcshdwr.boot;

import org.adorsys.adkcloak.adapter.file.FileBasedKeycloakConfigResolver;

public class CdrKeycloakConfigResolver extends FileBasedKeycloakConfigResolver {

	@Override
	protected String getResource() {
		return "adcshdwr.server";
	}
}
