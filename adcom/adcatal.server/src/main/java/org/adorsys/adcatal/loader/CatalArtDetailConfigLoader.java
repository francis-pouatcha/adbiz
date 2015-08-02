package org.adorsys.adcatal.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.adorsys.adcatal.rest.CatalArtDetailConfigEJB;
import org.adorsys.adcatal.rest.CatalArtDetailConfigLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class CatalArtDetailConfigLoader extends
		CoreAbstEntityLoader<CatalArtDetailConfig> {

	@Inject
	private CatalArtDetailConfigEJB ejb;
	@Inject
	private CatalArtDetailConfigLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB
	private CatalArtDetailConfigLoader loader;
	
	@Override
	protected CoreAbstIdentifLookup<CatalArtDetailConfig> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalArtDetailConfig> getEjb() {
		return ejb;
	}

	@Override
	protected CatalArtDetailConfig newObject() {
		return new CatalArtDetailConfig();
	}

	@Override
	protected CoreAbstLoader<CatalArtDetailConfig> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}
}
