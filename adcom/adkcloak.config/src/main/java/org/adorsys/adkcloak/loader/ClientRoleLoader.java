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
public class ClientRoleLoader extends CoreAbstModelLoader<ClientRoleReprestn> {

	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private ClientRoleLoader loader;	
	@Inject
	private RealmClient realmClient;

	@Override
	protected ClientRoleReprestn newObject() {
		return new ClientRoleReprestn();
	}
	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

	@Override
	protected CoreAbstLoader<ClientRoleReprestn> getLoader() {
		return loader;
	}

	@Override
	protected Object getIdentif(ClientRoleReprestn t) {
		return t;
	}
	@Override
	protected ClientRoleReprestn lookup(Object identif) {
		try {
			ClientRoleReprestn id = (ClientRoleReprestn) identif;
			return realmClient.findClientRole(id.getRealmId(), id.getClientId(),id.getName());
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
	@Override
	protected ClientRoleReprestn update(ClientRoleReprestn t) {
		return t;
	}
	@Override
	protected ClientRoleReprestn create(ClientRoleReprestn t) {
		try {
			realmClient.createClientRole(t);
			return realmClient.findClientRole(t.getRealmId(), t.getClientId(),t.getName());
		} catch (Failure e) {
			throw new IllegalStateException(e);
		}
	}
}
