package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Workspace;
import org.adorsys.adbase.rest.WorkspaceEJB;
import org.adorsys.adbase.rest.WorkspaceLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class WorkspaceLoader  extends CoreAbstEntityLoader<Workspace> {

	@Inject
	private WorkspaceEJB ejb;
	@Inject
	private WorkspaceLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private WorkspaceLoader loader;	

	@Override
	protected Workspace newObject() {
		return new Workspace();
	}

	@Override
	protected CoreAbstIdentifLookup<Workspace> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<Workspace> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<Workspace> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
