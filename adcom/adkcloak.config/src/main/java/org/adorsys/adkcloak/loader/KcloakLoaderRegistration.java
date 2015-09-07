package org.adorsys.adkcloak.loader;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.adorsys.adcore.loader.jpa.CorLdrJob;
import org.adorsys.adcore.loader.jpa.CorLdrPrcssngStep;
import org.adorsys.adcore.loader.jpa.CorLdrStep;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.xls.AbstractLoader;
import org.adorsys.adcore.xls.CoreAbstLoaderRegistration;
import org.keycloak.representations.idm.RealmRepresentation;

@Startup
@Singleton
public class KcloakLoaderRegistration extends CoreAbstLoaderRegistration {

	@Inject
	private DataSheetLoader dataSheetLoader;
	@Inject
	private RealmLoader realmLoader;
	@Inject
	private ClientLoader clientLoader;

	@EJB
	private KcloakLoaderRegistration registration;
	@EJB
	private KcloakLoaderExecutor execTask;
	
	@PostConstruct
	public void postConstruct(){
		super.postConstruct();
		dataSheetLoader.registerLoader(RealmRepresentation.class.getSimpleName(), realmLoader);
		dataSheetLoader.registerLoader(ClientReprestn.class.getSimpleName(), clientLoader);
	}

	@Override
	protected AbstractLoader getDataSheetLoader() {
		return dataSheetLoader;
	}

	@Override
	protected CoreAbstEntityJobExecutor<CorLdrJob, CorLdrStep, CorLdrPrcssngStep> getExecTask() {
		return execTask;
	}

	@Override
	protected CoreAbstEntityJobExecutor<CorLdrJob, CorLdrStep, CorLdrPrcssngStep> getEjb() {
		return registration;
	}
}
