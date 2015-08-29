package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.BaseCountryName;
import org.adorsys.adbase.rest.BaseCountryNameEJB;
import org.adorsys.adbase.rest.BaseCountryNameLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class BaseCountryNameLoader extends CoreAbstEntityLoader<BaseCountryName> {

	@Inject
	private BaseCountryNameEJB ejb;
	@Inject
	private BaseCountryNameLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private BaseCountryNameLoader loader;	

	@Override
	protected BaseCountryName newObject() {
		return new BaseCountryName();
	}

	@Override
	protected CoreAbstIdentifLookup<BaseCountryName> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<BaseCountryName> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<BaseCountryName> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
