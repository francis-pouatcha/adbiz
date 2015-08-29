package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OuWorkspace;
import org.adorsys.adbase.rest.OuWorkspaceEJB;
import org.adorsys.adbase.rest.OuWorkspaceLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class OuWorkspaceLoader  extends CoreAbstEntityLoader<OuWorkspace> {

	@Inject
	private OuWorkspaceEJB ejb;
	@Inject
	private OuWorkspaceLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private OuWorkspaceLoader loader;	

	@Override
	protected OuWorkspace newObject() {
		return new OuWorkspace();
	}

	@Override
	protected CoreAbstIdentifLookup<OuWorkspace> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<OuWorkspace> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<OuWorkspace> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
