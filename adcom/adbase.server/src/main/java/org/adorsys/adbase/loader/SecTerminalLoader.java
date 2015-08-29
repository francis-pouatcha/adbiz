package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.SecTerminal;
import org.adorsys.adbase.rest.SecTerminalEJB;
import org.adorsys.adbase.rest.SecTerminalLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class SecTerminalLoader  extends CoreAbstEntityLoader<SecTerminal> {

	@Inject
	private SecTerminalEJB ejb;
	@Inject
	private SecTerminalLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private SecTerminalLoader loader;	

	@Override
	protected SecTerminal newObject() {
		return new SecTerminal();
	}

	@Override
	protected CoreAbstIdentifLookup<SecTerminal> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<SecTerminal> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<SecTerminal> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
