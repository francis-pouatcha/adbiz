package org.adorsys.adcatal.loader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalProdFmly;
import org.adorsys.adcatal.rest.CatalProdFmlyEJB;
import org.adorsys.adcatal.rest.CatalProdFmlyLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;

@Stateless
public class CatalProdFmlyLoader extends
		CoreAbstEntityLoader<CatalProdFmly> {
	@Inject
	private CatalProdFmlyEJB ejb;
	@Inject
	private CatalProdFmlyLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<CatalProdFmly> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalProdFmly> getEjb() {
		return ejb;
	}

	@Override
	protected CatalProdFmly newObject() {
		return new CatalProdFmly();
	}

}
