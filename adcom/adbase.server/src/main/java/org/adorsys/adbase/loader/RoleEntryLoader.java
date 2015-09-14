package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.RoleEntry;
import org.adorsys.adbase.rest.RoleEntryEJB;
import org.adorsys.adbase.rest.RoleEntryLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class RoleEntryLoader  extends CoreAbstEntityLoader<RoleEntry> {

	@Inject
	private RoleEntryEJB ejb;
	@Inject
	private RoleEntryLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private RoleEntryLoader loader;	

	@Override
	protected RoleEntry newObject() {
		return new RoleEntry();
	}

	@Override
	protected CoreAbstIdentifLookup<RoleEntry> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<RoleEntry> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<RoleEntry> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
