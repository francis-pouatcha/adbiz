package org.adorsys.adbase.loader;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.BaseCountryName;
import org.adorsys.adbase.jpa.ConverterCurrRate;
import org.adorsys.adbase.jpa.Country;
import org.adorsys.adbase.jpa.Locality;
import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.jpa.OrgContact;
import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.jpa.OuType;
import org.adorsys.adbase.jpa.OuWorkspace;
import org.adorsys.adbase.jpa.OuWsRestriction;
import org.adorsys.adbase.jpa.PermEntry;
import org.adorsys.adbase.jpa.PricingCurrRate;
import org.adorsys.adbase.jpa.RoleEntry;
import org.adorsys.adbase.jpa.SecTermRegist;
import org.adorsys.adbase.jpa.SecTerminal;
import org.adorsys.adbase.jpa.UserWorkspace;
import org.adorsys.adbase.jpa.UserWsRestriction;
import org.adorsys.adbase.jpa.Workspace;
import org.adorsys.adbase.jpa.WorkspaceRestriction;
import org.adorsys.adcore.loader.jpa.CorLdrJob;
import org.adorsys.adcore.loader.jpa.CorLdrPrcssngStep;
import org.adorsys.adcore.loader.jpa.CorLdrStep;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.xls.AbstractLoader;
import org.adorsys.adcore.xls.CoreAbstLoaderRegistration;

@Startup
@Singleton
public class BaseLoaderRegistration extends CoreAbstLoaderRegistration {

	@Inject
	private DataSheetLoader dataSheetLoader;
	@Inject
	private ConverterCurrRateLoader converterCurrRateLoader;
	@Inject
	private CountryLoader countryLoader;
	@Inject
	private LocalityLoader localityLoader;
	@Inject
	private LoginLoader loginLoader;
	@Inject
	private OrgContactLoader orgContactLoader;
	@Inject
	private OrgUnitLoader orgUnitLoader;
	@Inject
	private OuTypeLoader ouTypeLoader;
	@Inject
	private OuWorkspaceLoader ouWorkspaceLoader;
	@Inject
	private OuWsRestrictionLoader ouWsRestrictionLoader;
	@Inject
	private PermEntryLoader permEntryLoader;
	@Inject
	private PricingCurrRateLoader pricingCurrRateLoader;
	@Inject
	private RoleEntryLoader roleEntryLoader;
	@Inject
	private UserWorkspaceLoader userWorkspaceLoader;
	@Inject
	private UserWsRestrictionLoader userWsRestrictionLoader;
	@Inject
	private WorkspaceLoader workspaceLoader;
	@Inject
	private WorkspaceRestrictionLoader workspaceRestrictionLoader;
	@Inject
	private SecTermRegistLoader secTermRegistLoader;
	@Inject
	private SecTerminalLoader secTerminalLoader;
	@Inject
	private BaseCountryNameLoader baseCountryNameLoader;
	
	@EJB
	private BaseLoaderRegistration registration;
	@EJB
	private BaseLoaderExecutor executor;
	
	@PostConstruct
	public void postConstruct(){
		dataSheetLoader.registerLoader(RoleEntry.class.getSimpleName(), roleEntryLoader);
		dataSheetLoader.registerLoader(PermEntry.class.getSimpleName(), permEntryLoader);
		dataSheetLoader.registerLoader(Country.class.getSimpleName(), countryLoader);
		dataSheetLoader.registerLoader(ConverterCurrRate.class.getSimpleName(), converterCurrRateLoader);
		dataSheetLoader.registerLoader(PricingCurrRate.class.getSimpleName(), pricingCurrRateLoader);
		dataSheetLoader.registerLoader(OuType.class.getSimpleName(), ouTypeLoader);
		dataSheetLoader.registerLoader(OrgUnit.class.getSimpleName(), orgUnitLoader);
		dataSheetLoader.registerLoader(OrgContact.class.getSimpleName(), orgContactLoader);
		dataSheetLoader.registerLoader(Locality.class.getSimpleName(), localityLoader);
		dataSheetLoader.registerLoader(Workspace.class.getSimpleName(), workspaceLoader);
		dataSheetLoader.registerLoader(WorkspaceRestriction.class.getSimpleName(), workspaceRestrictionLoader);
		dataSheetLoader.registerLoader(OuWorkspace.class.getSimpleName(), ouWorkspaceLoader);
		dataSheetLoader.registerLoader(OuWsRestriction.class.getSimpleName(), ouWsRestrictionLoader);
		dataSheetLoader.registerLoader(Login.class.getSimpleName(), loginLoader);
		dataSheetLoader.registerLoader(UserWorkspace.class.getSimpleName(), userWorkspaceLoader);
		dataSheetLoader.registerLoader(UserWsRestriction.class.getSimpleName(), userWsRestrictionLoader);
		dataSheetLoader.registerLoader(SecTermRegist.class.getSimpleName(), secTermRegistLoader);
		dataSheetLoader.registerLoader(SecTerminal.class.getSimpleName(), secTerminalLoader);
		dataSheetLoader.registerLoader(BaseCountryName.class.getSimpleName(),baseCountryNameLoader);
	}

	@Override
	protected AbstractLoader getDataSheetLoader() {
		return dataSheetLoader;
	}

	@Override
	protected CoreAbstEntityJobExecutor<CorLdrJob, CorLdrStep, CorLdrPrcssngStep> getExecTask() {
		return executor;
	}

	@Override
	protected CoreAbstEntityJobExecutor<CorLdrJob, CorLdrStep, CorLdrPrcssngStep> getEjb() {
		return registration;
	}
}
