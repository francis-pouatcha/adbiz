package org.adorsys.adcatal.loader;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArt2ProductFamily;
import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.adorsys.adcatal.jpa.CatalArtEquivalence;
import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.jpa.CatalPicMapping;
import org.adorsys.adcatal.jpa.CatalProdFmly;
import org.adorsys.adcatal.jpa.CatalProdFmlyLangMap;
import org.adorsys.adcore.loader.jpa.CorLdrJob;
import org.adorsys.adcore.loader.jpa.CorLdrPrcssngStep;
import org.adorsys.adcore.loader.jpa.CorLdrStep;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.xls.AbstractLoader;
import org.adorsys.adcore.xls.CoreAbstLoaderRegistration;

@Startup
@Singleton
public class CatalLoaderRegistration extends CoreAbstLoaderRegistration {

	@Inject
	private DataSheetLoader dataSheetLoader;
	@Inject
	private CatalArtDetailConfigLoader catalArtDetailConfigLoader;
	@Inject
	private CatalArtEquivalenceLoader catalArtEquivalenceLoader;
	@Inject
	private CatalArticleLoader catalArticleLoader;
	@Inject
	private CatalArtLangMappingLoader catalArtLangMappingLoader;
	@Inject
	private CatalArtManufSuppLoader catalArtManufSuppLoader;
	@Inject
	private CatalPicMappingLoader catalPicMappingLoader;
	@Inject
	private CatalProdFmlyLoader catalProductFamilyLoader;
	@Inject
	private CatalProdFmlyLangMapLoader catalProdFmlyLangMapLoader;
	@Inject
	private CatalArt2ProductFamilyLoader catalArt2ProductFamilyLoader;
	@EJB
	private CatalLoaderRegistration registration;
	@EJB
	private CatalLoaderExecutor execTask;
	
	@PostConstruct
	public void postConstruct(){
		super.postConstruct();
		dataSheetLoader.registerLoader(CatalArtDetailConfig.class.getSimpleName(), catalArtDetailConfigLoader);
		dataSheetLoader.registerLoader(CatalArtEquivalence.class.getSimpleName(), catalArtEquivalenceLoader);
		dataSheetLoader.registerLoader(CatalArticle.class.getSimpleName(), catalArticleLoader);
		dataSheetLoader.registerLoader(CatalArtLangMapping.class.getSimpleName(), catalArtLangMappingLoader);
		dataSheetLoader.registerLoader(CatalArtManufSupp.class.getSimpleName(), catalArtManufSuppLoader);
		dataSheetLoader.registerLoader(CatalPicMapping.class.getSimpleName(), catalPicMappingLoader);
		dataSheetLoader.registerLoader(CatalProdFmly.class.getSimpleName(), catalProductFamilyLoader);
		dataSheetLoader.registerLoader(CatalProdFmlyLangMap.class.getSimpleName(), catalProdFmlyLangMapLoader);
		dataSheetLoader.registerLoader(CatalArt2ProductFamily.class.getSimpleName(), catalArt2ProductFamilyLoader);
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
