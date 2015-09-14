package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OuType;
import org.adorsys.adbase.rest.OuTypeEJB;
import org.adorsys.adbase.rest.OuTypeLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class OuTypeLoader  extends CoreAbstEntityLoader<OuType> {

	@Inject
	private OuTypeEJB ejb;
	@Inject
	private OuTypeLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private OuTypeLoader loader;	

	@Override
	protected OuType newObject() {
		return new OuType();
	}

	@Override
	protected CoreAbstIdentifLookup<OuType> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<OuType> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<OuType> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
