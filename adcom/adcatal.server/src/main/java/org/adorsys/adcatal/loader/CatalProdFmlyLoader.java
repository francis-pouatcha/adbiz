package org.adorsys.adcatal.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalProdFmly;
import org.adorsys.adcatal.rest.CatalProdFmlyEJB;
import org.adorsys.adcatal.rest.CatalProdFmlyLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class CatalProdFmlyLoader extends
		CoreAbstEntityLoader<CatalProdFmly> {
	@Inject
	private CatalProdFmlyEJB ejb;
	@Inject
	private CatalProdFmlyLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB
	private CatalProdFmlyLoader loader;

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

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

	@Override
	protected CoreAbstLoader<CatalProdFmly> getLoader() {
		return loader;
	}
}
