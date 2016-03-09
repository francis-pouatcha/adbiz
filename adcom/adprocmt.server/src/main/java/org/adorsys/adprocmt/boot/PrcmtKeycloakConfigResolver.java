package org.adorsys.adprocmt.boot;

import org.adorsys.adkcloak.adapter.file.FileBasedKeycloakConfigResolver;

public class PrcmtKeycloakConfigResolver extends FileBasedKeycloakConfigResolver {

	@Override
	protected String getResource() {
		return "adprocmt.server";
	}
}
