package org.adorsys.adcatal.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtEquivalence;
import org.adorsys.adcatal.rest.CatalArtEquivalenceEJB;
import org.adorsys.adcatal.rest.CatalArtEquivalenceLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class CatalArtEquivalenceLoader extends
		CoreAbstEntityLoader<CatalArtEquivalence> {
	@Inject
	private CatalArtEquivalenceEJB ejb;
	@Inject
	private CatalArtEquivalenceLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB
	private CatalArtEquivalenceLoader loader;
	
	@Override
	protected CoreAbstIdentifLookup<CatalArtEquivalence> getLookup() {
		return lookup;
	}
	@Override
	protected CoreAbstIdentifiedEJB<CatalArtEquivalence> getEjb() {
		return ejb;
	}
	@Override
	protected CatalArtEquivalence newObject() {
		return new CatalArtEquivalence();
	}
	@Override
	protected CoreAbstLoader<CatalArtEquivalence> getLoader() {
		return loader;
	}
	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}
}
