package org.adorsys.adcatal.loader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalPicMapping;
import org.adorsys.adcatal.rest.CatalPicMappingEJB;
import org.adorsys.adcatal.rest.CatalPicMappingLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;
import org.adorsys.adcore.xls.CoreAbstObjectLoader;

@Stateless
public class CatalPicMappingLoader extends
		CoreAbstObjectLoader<CatalPicMapping> {

	@Inject
	private CatalPicMappingEJB ejb;
	@Inject
	private CatalPicMappingLookup lookup;

	@Override
	protected CoreAbstIdentifiedLookup<CatalPicMapping> getLookup() {
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
