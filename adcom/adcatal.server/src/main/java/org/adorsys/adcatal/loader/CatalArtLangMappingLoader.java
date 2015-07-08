package org.adorsys.adcatal.loader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.rest.CatalArtLangMappingEJB;
import org.adorsys.adcatal.rest.CatalArtLangMappingLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;
import org.adorsys.adcore.xls.CoreAbstObjectLoader;

@Stateless
public class CatalArtLangMappingLoader extends
		CoreAbstObjectLoader<CatalArtLangMapping> {
	@Inject
	private CatalArtLangMappingEJB ejb;
	@Inject
	private CatalArtLangMappingLookup lookup;
	
	@Override
	protected CoreAbstIdentifiedLookup<CatalArtLangMapping> getLookup() {
		return lookup;
	}
	@Override
	protected CoreAbstIdentifiedEJB<CatalArtLangMapping> getEjb() {
		return ejb;
	}
	@Override
	protected CatalArtLangMapping newObject() {
		return new CatalArtLangMapping();
	}

}
