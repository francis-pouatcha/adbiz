package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.ConverterCurrRate;
import org.adorsys.adbase.rest.ConverterCurrRateEJB;
import org.adorsys.adbase.rest.ConverterCurrRateLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class ConverterCurrRateLoader extends CoreAbstEntityLoader<ConverterCurrRate> {

	@Inject
	private ConverterCurrRateEJB ejb;
	@Inject
	private ConverterCurrRateLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private ConverterCurrRateLoader loader;	
	
	@Override
	protected ConverterCurrRate newObject() {
		return new ConverterCurrRate();
	}

	@Override
	protected CoreAbstIdentifLookup<ConverterCurrRate> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<ConverterCurrRate> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<ConverterCurrRate> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
