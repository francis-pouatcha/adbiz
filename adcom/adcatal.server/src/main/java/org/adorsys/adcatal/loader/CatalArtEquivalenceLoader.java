package org.adorsys.adcatal.loader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtEquivalence;
import org.adorsys.adcatal.rest.CatalArtEquivalenceEJB;
import org.adorsys.adcatal.rest.CatalArtEquivalenceLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;

@Stateless
public class CatalArtEquivalenceLoader extends
		CoreAbstEntityLoader<CatalArtEquivalence> {
	@Inject
	private CatalArtEquivalenceEJB ejb;
	@Inject
	private CatalArtEquivalenceLookup lookup;
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
}
