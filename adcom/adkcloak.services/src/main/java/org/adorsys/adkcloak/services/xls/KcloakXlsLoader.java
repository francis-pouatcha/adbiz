package org.adorsys.adkcloak.services.xls;

import org.adorsys.adcore.xls.AbstractLoader;
import org.keycloak.models.KeycloakSession;
import org.keycloak.representations.idm.RealmRepresentation;

public class KcloakXlsLoader {

	private DataSheetLoader dataSheetLoader = new DataSheetLoader();
	
	public KcloakXlsLoader(KeycloakSession session,RealmClient realmClient){
		dataSheetLoader.registerLoader(RealmRepresentation.class.getSimpleName(), new RealmLoader(session,realmClient));
		dataSheetLoader.registerLoader(ClientReprestn.class.getSimpleName(), new ClientLoader(session,realmClient));
		dataSheetLoader.registerLoader(ClientRoleReprestn.class.getSimpleName(), new ClientRoleLoader(session,realmClient));
		dataSheetLoader.registerLoader(RolesReprestn.class.getSimpleName(), new RolesLoader(session,realmClient));
		dataSheetLoader.registerLoader(UserReprestn.class.getSimpleName(), new UserLoader(session,realmClient));
		dataSheetLoader.registerLoader(UserCredentials.class.getSimpleName(), new UserCredentialsLoader(session,realmClient));
		dataSheetLoader.registerLoader(UserClientRoles.class.getSimpleName(), new UserClientRolesLoader(session,realmClient));
		dataSheetLoader.registerLoader(UserRealmRoles.class.getSimpleName(), new UserRealmRolesLoader(session, realmClient));
	}

	public AbstractLoader getDataSheetLoader() {
		return dataSheetLoader;
	}
}
