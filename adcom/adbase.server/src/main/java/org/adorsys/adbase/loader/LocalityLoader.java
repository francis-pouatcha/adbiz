package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Locality;
import org.adorsys.adbase.rest.LocalityEJB;
import org.adorsys.adbase.rest.LocalityLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class LocalityLoader  extends CoreAbstEntityLoader<Locality> {

	@Inject
	private LocalityEJB ejb;
	@Inject
	private LocalityLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private LocalityLoader loader;	

	@Override
	protected Locality newObject() {
		return new Locality();
	}

	@Override
	protected CoreAbstIdentifLookup<Locality> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<Locality> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<Locality> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
