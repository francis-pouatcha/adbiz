package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OuWsRestriction;
import org.adorsys.adbase.rest.OuWsRestrictionEJB;
import org.adorsys.adbase.rest.OuWsRestrictionLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class OuWsRestrictionLoader  extends CoreAbstEntityLoader<OuWsRestriction> {

	@Inject
	private OuWsRestrictionEJB ejb;
	@Inject
	private OuWsRestrictionLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private OuWsRestrictionLoader loader;	

	@Override
	protected OuWsRestriction newObject() {
		return new OuWsRestriction();
	}

	@Override
	protected CoreAbstIdentifLookup<OuWsRestriction> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<OuWsRestriction> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<OuWsRestriction> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
