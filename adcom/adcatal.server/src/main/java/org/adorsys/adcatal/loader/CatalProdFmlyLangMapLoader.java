package org.adorsys.adcatal.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalProdFmlyLangMap;
import org.adorsys.adcatal.rest.CatalProdFmlyLangMapEJB;
import org.adorsys.adcatal.rest.CatalProdFmlyLangMapLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class CatalProdFmlyLangMapLoader extends
		CoreAbstEntityLoader<CatalProdFmlyLangMap> {
	@Inject
	private CatalProdFmlyLangMapEJB ejb;
	@Inject
	private CatalProdFmlyLangMapLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB
	private CatalProdFmlyLangMapLoader loader;

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

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

	@Override
	protected CoreAbstLoader<CatalProdFmlyLangMap> getLoader() {
		return loader;
	}
}
