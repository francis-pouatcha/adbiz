package org.adorsys.adcatal.loader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalPicMapping;
import org.adorsys.adcatal.rest.CatalPicMappingEJB;
import org.adorsys.adcatal.rest.CatalPicMappingLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;

@Stateless
public class CatalPicMappingLoader extends
		CoreAbstEntityLoader<CatalPicMapping> {

	@Inject
	private CatalPicMappingEJB ejb;
	@Inject
	private CatalPicMappingLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<CatalPicMapping> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalPicMapping> getEjb() {
		return ejb;
	}

	@Override
	protected CatalPicMapping newObject() {
		return new CatalPicMapping();
	}
}
