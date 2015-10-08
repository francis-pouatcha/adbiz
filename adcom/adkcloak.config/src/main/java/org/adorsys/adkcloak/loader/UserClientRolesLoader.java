package org.adorsys.adkcloak.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;
import org.adorsys.adkcloak.config.Failure;
import org.adorsys.adkcloak.config.RealmClient;

@Stateless
public class UserClientRolesLoader extends CoreAbstModelLoader<UserClientRoles> {

	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private UserClientRolesLoader loader;	
	@Inject
	private RealmClient realmClient;

	@Override
	protected UserClientRoles newObject() {
		return new UserClientRoles();
	}
	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

	@Override
	protected CoreAbstLoader<UserClientRoles> getLoader() {
		return loader;
	}

	@Override
	protected Object getIdentif(UserClientRoles t) {
		return t;
	}
	@Override
	protected UserClientRoles lookup(Object identif) {
		try {
			UserClientRoles id = (UserClientRoles) identif;
			UserReprestn userReprestn = realmClient.findUser(id.getRealmId(), id.getUsername());
			if(userReprestn==null) return null;
			return realmClient.findUserClientRole(id);
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
	@Override
	protected UserClientRoles update(UserClientRoles t) {
		return t;
	}
	@Override
	protected UserClientRoles create(UserClientRoles t) {
		try {
			realmClient.addClientRole(t);
			return lookup(t);
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
}
