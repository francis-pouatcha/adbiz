package org.adorsys.adcatal.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalPicMapping;
import org.adorsys.adcatal.rest.CatalPicMappingEJB;
import org.adorsys.adcatal.rest.CatalPicMappingLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class CatalPicMappingLoader extends
		CoreAbstEntityLoader<CatalPicMapping> {

	@Inject
	private CatalPicMappingEJB ejb;
	@Inject
	private CatalPicMappingLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB
	private CatalPicMappingLoader loader;

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
	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

	@Override
	protected CoreAbstLoader<CatalPicMapping> getLoader() {
		return loader;
	}
}
