package org.adorsys.adkcloak.services.xls;

import java.util.List;

import org.adorsys.adcore.xls.CopyUtils;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.spi.Failure;
import org.keycloak.models.KeycloakSession;
import org.keycloak.representations.idm.CredentialRepresentation;

public class UserCredentialsLoader extends CoreAbstModelLoader<UserCredentials> {

	private RealmClient realmClient;

	public UserCredentialsLoader(KeycloakSession session,RealmClient realmClient) {
		super(session);
		this.realmClient = realmClient;
	}

	@Override
	protected UserCredentials newObject() {
		return new UserCredentials();
	}

	@Override
	protected CoreAbstLoader<UserCredentials> getLoader() {
		return this;
	}

	@Override
	protected Object getIdentif(UserCredentials t) {
		return t;
	}
	@Override
	protected UserCredentials lookup(Object identif) {
		try {
			UserCredentials id = (UserCredentials) identif;
			UserReprestn userReprestn = realmClient.findUser(id.getRealmId(), id.getUsername());
			if(userReprestn==null) return null;
			List<CredentialRepresentation> credentials = userReprestn.getCredentials();
			if(credentials!=null){
				for (CredentialRepresentation cr : credentials) {
					if(StringUtils.equalsIgnoreCase(id.getType(), cr.getType())) {
						UserCredentials uc = new UserCredentials();
						CopyUtils.copy(uc, cr);
						uc.setRealmId(id.getRealmId());
						uc.setUsername(id.getUsername());
						return uc;
					}
				}
			}
			return null;
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
	@Override
	protected UserCredentials update(UserCredentials t) {
		return t;
	}
	@Override
	protected UserCredentials create(UserCredentials t) {
		realmClient.addCredential(t);
		return t;
	}
}
