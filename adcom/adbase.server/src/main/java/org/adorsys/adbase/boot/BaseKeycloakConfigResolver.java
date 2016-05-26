package org.adorsys.adbase.boot;

import org.adorsys.adkcloak.adapter.file.FileBasedKeycloakConfigResolver;

public class BaseKeycloakConfigResolver extends FileBasedKeycloakConfigResolver {

	@Override
	protected String getResource() {
		return "adbase.server";
	}
}
