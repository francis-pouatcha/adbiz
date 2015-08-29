package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.PricingCurrRate;
import org.adorsys.adbase.rest.PricingCurrRateEJB;
import org.adorsys.adbase.rest.PricingCurrRateLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class PricingCurrRateLoader  extends CoreAbstEntityLoader<PricingCurrRate> {

	@Inject
	private PricingCurrRateEJB ejb;
	@Inject
	private PricingCurrRateLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private PricingCurrRateLoader loader;	

	@Override
	protected PricingCurrRate newObject() {
		return new PricingCurrRate();
	}

	@Override
	protected CoreAbstIdentifLookup<PricingCurrRate> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<PricingCurrRate> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<PricingCurrRate> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
