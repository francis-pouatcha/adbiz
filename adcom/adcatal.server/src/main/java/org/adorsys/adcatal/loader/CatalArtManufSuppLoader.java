package org.adorsys.adcatal.loader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcatal.rest.CatalArtManufSuppEJB;
import org.adorsys.adcatal.rest.CatalArtManufSuppLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;
import org.adorsys.adcore.xls.CoreAbstObjectLoader;

@Stateless
public class CatalArtManufSuppLoader extends
		CoreAbstObjectLoader<CatalArtManufSupp> {
	@Inject
	private CatalArtManufSuppEJB ejb;
	@Inject
	private CatalArtManufSuppLookup lookup;

	@Override
	protected CoreAbstIdentifiedLookup<CatalArtManufSupp> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalArtManufSupp> getEjb() {
		return ejb;
	}

	@Override
	protected CatalArtManufSupp newObject() {
		return new CatalArtManufSupp();
	}
}
