package org.adorsys.adstock.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;
import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.rest.StkSectionEJB;
import org.adorsys.adstock.rest.StkSectionLookup;

@Stateless
public class StkSectionLoader extends CoreAbstEntityLoader<StkSection> {

	@Inject
	private StkSectionEJB ejb;
	@Inject
	private StkSectionLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB
	private StkSectionLoader loader;

	@Override
	protected StkSection newObject() {
		return new StkSection();
	}

	@Override
	protected CoreAbstIdentifLookup<StkSection> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<StkSection> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<StkSection> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}
}
