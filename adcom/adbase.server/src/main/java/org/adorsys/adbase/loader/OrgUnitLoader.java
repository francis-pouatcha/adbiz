package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.rest.OrgUnitEJB;
import org.adorsys.adbase.rest.OrgUnitLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class OrgUnitLoader  extends CoreAbstEntityLoader<OrgUnit> {

	@Inject
	private OrgUnitEJB ejb;
	@Inject
	private OrgUnitLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private OrgUnitLoader loader;	

	@Override
	protected OrgUnit newObject() {
		return new OrgUnit();
	}

	@Override
	protected CoreAbstIdentifLookup<OrgUnit> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<OrgUnit> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<OrgUnit> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
