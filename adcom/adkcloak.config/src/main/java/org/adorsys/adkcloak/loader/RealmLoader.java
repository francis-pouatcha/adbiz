package org.adorsys.adkcloak.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;
import org.adorsys.adkcloak.config.Failure;
import org.adorsys.adkcloak.config.RealmClient;
import org.keycloak.representations.idm.RealmRepresentation;

@Stateless
public class RealmLoader extends CoreAbstModelLoader<RealmRepresentation> {

	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private RealmLoader loader;	
	@Inject
	private RealmClient realmClient;

	@Override
	protected RealmRepresentation newObject() {
		return new RealmRepresentation();
	}
	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

	@Override
	protected CoreAbstLoader<RealmRepresentation> getLoader() {
		return loader;
	}

	@Override
	protected Object getIdentif(RealmRepresentation t) {
		return t.getId();
	}
	@Override
	protected RealmRepresentation lookup(Object identif) {
		try {
			return realmClient.findRealm((String) identif);
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
	@Override
	protected RealmRepresentation update(RealmRepresentation t) {
		return t;
	}
	@Override
	protected RealmRepresentation create(RealmRepresentation t) {
		try {
			realmClient.createRealm(t);
			return realmClient.findRealm(t.getRealm());
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
	
}
