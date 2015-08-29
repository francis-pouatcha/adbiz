package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.WorkspaceRestriction;
import org.adorsys.adbase.rest.WorkspaceRestrictionEJB;
import org.adorsys.adbase.rest.WorkspaceRestrictionLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class WorkspaceRestrictionLoader  extends CoreAbstEntityLoader<WorkspaceRestriction> {

	@Inject
	private WorkspaceRestrictionEJB ejb;
	@Inject
	private WorkspaceRestrictionLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private WorkspaceRestrictionLoader loader;	

	@Override
	protected WorkspaceRestriction newObject() {
		return new WorkspaceRestriction();
	}

	@Override
	protected CoreAbstIdentifLookup<WorkspaceRestriction> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<WorkspaceRestriction> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<WorkspaceRestriction> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
