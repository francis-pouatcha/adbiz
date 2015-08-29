package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Country;
import org.adorsys.adbase.rest.CountryEJB;
import org.adorsys.adbase.rest.CountryLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class CountryLoader extends CoreAbstEntityLoader<Country> {

	@Inject
	private CountryEJB ejb;
	@Inject
	private CountryLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private CountryLoader loader;	
	
	
	@Override
	protected Country newObject() {
		return new Country();
	}


	@Override
	protected CoreAbstIdentifLookup<Country> getLookup() {
		return lookup;
	}


	@Override
	protected CoreAbstIdentifiedEJB<Country> getEjb() {
		return ejb;
	}


	@Override
	protected CoreAbstLoader<Country> getLoader() {
		return loader;
	}


	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
