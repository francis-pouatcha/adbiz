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
	@Inject
	private ClientRoleLoader clientRoleLoader;
	@Inject
	private RolesLoader rolesLoader;
	@Inject
	private UserLoader userLoader;
	@Inject
	private UserCredentialsLoader userCredentialsLoader;
	@Inject
	private DeployConfigLoader deployConfigLoader;
	@Inject
	private UserClientRolesLoader userClientRolesLoader;
	@EJB
	private KcloakLoaderRegistration registration;
	@EJB
	private KcloakLoaderExecutor execTask;
	
	@PostConstruct
	public void postConstruct(){
		super.postConstruct();
		dataSheetLoader.registerLoader(RealmRepresentation.class.getSimpleName(), realmLoader);
		dataSheetLoader.registerLoader(ClientReprestn.class.getSimpleName(), clientLoader);
		dataSheetLoader.registerLoader(ClientRoleReprestn.class.getSimpleName(), clientRoleLoader);
		dataSheetLoader.registerLoader(RolesReprestn.class.getSimpleName(), rolesLoader);
		dataSheetLoader.registerLoader(UserReprestn.class.getSimpleName(), userLoader);
		dataSheetLoader.registerLoader(UserCredentials.class.getSimpleName(), userCredentialsLoader);
		dataSheetLoader.registerLoader(DeployConfig.class.getSimpleName(), deployConfigLoader);
		dataSheetLoader.registerLoader(UserClientRoles.class.getSimpleName(), userClientRolesLoader);
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
