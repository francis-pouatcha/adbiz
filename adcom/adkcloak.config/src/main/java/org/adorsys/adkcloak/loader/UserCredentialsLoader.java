package org.adorsys.adkcloak.loader;

import java.util.List;

import javax.ejb.EJB;	
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;
import org.adorsys.adkcloak.config.Failure;
import org.adorsys.adkcloak.config.RealmClient;
import org.adorsys.adkcloak.utils.CopyUtils;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.representations.idm.CredentialRepresentation;

@Stateless
public class UserCredentialsLoader extends CoreAbstModelLoader<UserCredentials> {

	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private UserCredentialsLoader loader;	
	@Inject
	private RealmClient realmClient;

	@Override
	protected UserCredentials newObject() {
		return new UserCredentials();
	}
	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

	@Override
	protected CoreAbstLoader<UserCredentials> getLoader() {
		return loader;
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
		try {
			realmClient.addCredential(t);
			return lookup(t);
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
}
