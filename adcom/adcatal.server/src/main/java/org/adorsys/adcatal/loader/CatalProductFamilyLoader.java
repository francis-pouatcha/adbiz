package org.adorsys.adcatal.loader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalProductFamily;
import org.adorsys.adcatal.rest.CatalProductFamilyEJB;
import org.adorsys.adcatal.rest.CatalProductFamilyLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;
import org.adorsys.adcore.xls.CoreAbstObjectLoader;

@Stateless
public class CatalProductFamilyLoader extends
		CoreAbstObjectLoader<CatalProductFamily> {
	@Inject
	private CatalProductFamilyEJB ejb;
	@Inject
	private CatalProductFamilyLookup lookup;

	@Override
	protected CoreAbstIdentifiedLookup<CatalProductFamily> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalProductFamily> getEjb() {
		return ejb;
	}

	@Override
	protected CatalProductFamily newObject() {
		return new CatalProductFamily();
	}

}
