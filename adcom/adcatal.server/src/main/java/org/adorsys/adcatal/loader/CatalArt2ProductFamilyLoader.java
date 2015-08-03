package org.adorsys.adcatal.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArt2ProductFamily;
import org.adorsys.adcatal.rest.CatalArt2ProductFamilyEJB;
import org.adorsys.adcatal.rest.CatalArt2ProductFamilyLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class CatalArt2ProductFamilyLoader extends
		CoreAbstEntityLoader<CatalArt2ProductFamily> {

	@Inject
	private CatalArt2ProductFamilyEJB ejb;
	@Inject
	private CatalArt2ProductFamilyLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB
	private CatalArt2ProductFamilyLoader loader;

	@Override
	protected CatalArt2ProductFamily newObject() {
		return new CatalArt2ProductFamily();
	}

	@Override
	protected CoreAbstIdentifLookup<CatalArt2ProductFamily> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalArt2ProductFamily> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<CatalArt2ProductFamily> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
