package org.adorsys.adcatal.loader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalProdFmlyLangMap;
import org.adorsys.adcatal.rest.CatalProdFmlyLangMapEJB;
import org.adorsys.adcatal.rest.CatalProdFmlyLangMapLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;

@Stateless
public class CatalProdFmlyLangMapLoader extends
		CoreAbstEntityLoader<CatalProdFmlyLangMap> {
	@Inject
	private CatalProdFmlyLangMapEJB ejb;
	@Inject
	private CatalProdFmlyLangMapLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<CatalProdFmlyLangMap> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalProdFmlyLangMap> getEjb() {
		return ejb;
	}

	@Override
	protected CatalProdFmlyLangMap newObject() {
		return new CatalProdFmlyLangMap();
	}

}
