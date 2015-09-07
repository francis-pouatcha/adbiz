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
public class ClientLoader extends CoreAbstModelLoader<ClientReprestn> {

	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private ClientLoader loader;	
	@Inject
	private RealmClient realmClient;

	@Override
	protected ClientReprestn newObject() {
		return new ClientReprestn();
	}
	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

	@Override
	protected CoreAbstLoader<ClientReprestn> getLoader() {
		return loader;
	}

	@Override
	protected Object getIdentif(ClientReprestn t) {
		return t;
	}
	@Override
	protected ClientReprestn lookup(Object identif) {
		try {
			ClientReprestn id = (ClientReprestn) identif;
			return realmClient.findClient(id.getRealmId(), id.getId());
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
	@Override
	protected ClientReprestn update(ClientReprestn t) {
		return t;
	}
	@Override
	protected ClientReprestn create(ClientReprestn t) {
		try {
			realmClient.createClient(t);
			return realmClient.findClient(t.getRealmId(), t.getId());
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
}
