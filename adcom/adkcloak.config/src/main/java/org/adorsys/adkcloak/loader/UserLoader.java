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
public class UserLoader extends CoreAbstModelLoader<UserReprestn> {

	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private UserLoader loader;	
	@Inject
	private RealmClient realmClient;

	@Override
	protected UserReprestn newObject() {
		return new UserReprestn();
	}
	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

	@Override
	protected CoreAbstLoader<UserReprestn> getLoader() {
		return loader;
	}

	@Override
	protected Object getIdentif(UserReprestn t) {
		return t;
	}
	@Override
	protected UserReprestn lookup(Object identif) {
		try {
			UserReprestn id = (UserReprestn) identif;
			return realmClient.findUser(id.getRealmId(), id.getUsername());
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
	@Override
	protected UserReprestn update(UserReprestn t) {
		return t;
	}
	@Override
	protected UserReprestn create(UserReprestn t) {
		try {
			realmClient.createUser(t);
			return realmClient.findUser(t.getRealmId(), t.getUsername());
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
}
