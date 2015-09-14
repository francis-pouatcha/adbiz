package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.UserWsRestriction;
import org.adorsys.adbase.rest.UserWsRestrictionEJB;
import org.adorsys.adbase.rest.UserWsRestrictionLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class UserWsRestrictionLoader  extends CoreAbstEntityLoader<UserWsRestriction> {

	@Inject
	private UserWsRestrictionEJB ejb;
	@Inject
	private UserWsRestrictionLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private UserWsRestrictionLoader loader;	

	@Override
	protected UserWsRestriction newObject() {
		return new UserWsRestriction();
	}

	@Override
	protected CoreAbstIdentifLookup<UserWsRestriction> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<UserWsRestriction> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<UserWsRestriction> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
