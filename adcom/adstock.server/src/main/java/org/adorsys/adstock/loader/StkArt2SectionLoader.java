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
import org.adorsys.adstock.jpa.StkArt2Section;
import org.adorsys.adstock.rest.StkArt2SectionEJB;
import org.adorsys.adstock.rest.StkArt2SectionLookup;

@Stateless
public class StkArt2SectionLoader extends CoreAbstEntityLoader<StkArt2Section> {

	@Inject
	private StkArt2SectionEJB ejb;
	@Inject
	private StkArt2SectionLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB
	private StkArt2SectionLoader loader;

	@Override
	protected StkArt2Section newObject() {
		return new StkArt2Section();
	}

	@Override
	protected CoreAbstIdentifLookup<StkArt2Section> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<StkArt2Section> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<StkArt2Section> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}
}
