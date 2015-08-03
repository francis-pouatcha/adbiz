package org.adorsys.adcatal.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcatal.rest.CatalArtManufSuppEJB;
import org.adorsys.adcatal.rest.CatalArtManufSuppLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class CatalArtManufSuppLoader extends
		CoreAbstEntityLoader<CatalArtManufSupp> {
	@Inject
	private CatalArtManufSuppEJB ejb;
	@Inject
	private CatalArtManufSuppLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB
	private CatalArtManufSuppLoader loader;

	@Override
	protected CoreAbstIdentifLookup<CatalArtManufSupp> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalArtManufSupp> getEjb() {
		return ejb;
	}

	@Override
	protected CatalArtManufSupp newObject() {
		return new CatalArtManufSupp();
	}
	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

	@Override
	protected CoreAbstLoader<CatalArtManufSupp> getLoader() {
		return loader;
	}
}
