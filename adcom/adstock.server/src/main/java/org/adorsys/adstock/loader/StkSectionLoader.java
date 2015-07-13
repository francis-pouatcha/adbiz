package org.adorsys.adstock.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;
import org.adorsys.adcore.xls.CoreAbstObjectLoader;
import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.rest.StkSectionEJB;
import org.adorsys.adstock.rest.StkSectionLookup;

@Stateless
public class StkSectionLoader extends CoreAbstObjectLoader<StkSection> {

	@Inject
	private StkSectionEJB ejb;
	@Inject
	private StkSectionLookup lookup;

	@Override
	protected StkSection newObject() {
		return new StkSection();
	}

	@Override
	protected CoreAbstIdentifiedLookup<StkSection> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<StkSection> getEjb() {
		return ejb;
	}
}
