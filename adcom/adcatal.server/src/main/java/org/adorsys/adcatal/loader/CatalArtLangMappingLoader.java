package org.adorsys.adcatal.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.rest.CatalArtLangMappingEJB;
import org.adorsys.adcatal.rest.CatalArtLangMappingLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class CatalArtLangMappingLoader extends
		CoreAbstEntityLoader<CatalArtLangMapping> {
	@Inject
	private CatalArtLangMappingEJB ejb;
	@Inject
	private CatalArtLangMappingLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private CatalArtLangMappingLoader loader;	
	
	@Override
	protected CoreAbstIdentifLookup<CatalArtLangMapping> getLookup() {
		return lookup;
	}
	@Override
	protected CoreAbstIdentifiedEJB<CatalArtLangMapping> getEjb() {
		return ejb;
	}
	@Override
	protected CatalArtLangMapping newObject() {
		return new CatalArtLangMapping();
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}
	@Override
	protected CoreAbstLoader<CatalArtLangMapping> getLoader() {
		return loader;
	}
}
