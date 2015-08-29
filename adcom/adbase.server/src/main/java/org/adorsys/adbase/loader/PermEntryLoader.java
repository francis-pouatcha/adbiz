package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.PermEntry;
import org.adorsys.adbase.rest.PermEntryEJB;
import org.adorsys.adbase.rest.PermEntryLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class PermEntryLoader  extends CoreAbstEntityLoader<PermEntry> {

	@Inject
	private PermEntryEJB ejb;
	@Inject
	private PermEntryLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private PermEntryLoader loader;	

	@Override
	protected PermEntry newObject() {
		return new PermEntry();
	}

	@Override
	protected CoreAbstIdentifLookup<PermEntry> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<PermEntry> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<PermEntry> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
