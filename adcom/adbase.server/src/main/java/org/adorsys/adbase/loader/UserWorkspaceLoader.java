package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.UserWorkspace;
import org.adorsys.adbase.rest.UserWorkspaceEJB;
import org.adorsys.adbase.rest.UserWorkspaceLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class UserWorkspaceLoader  extends CoreAbstEntityLoader<UserWorkspace> {

	@Inject
	private UserWorkspaceEJB ejb;
	@Inject
	private UserWorkspaceLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private UserWorkspaceLoader loader;	

	@Override
	protected UserWorkspace newObject() {
		return new UserWorkspace();
	}

	@Override
	protected CoreAbstIdentifLookup<UserWorkspace> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<UserWorkspace> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<UserWorkspace> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
