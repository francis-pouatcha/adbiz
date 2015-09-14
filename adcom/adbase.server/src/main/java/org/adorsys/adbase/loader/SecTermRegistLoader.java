package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.SecTermRegist;
import org.adorsys.adbase.rest.SecTermRegistEJB;
import org.adorsys.adbase.rest.SecTermRegistLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class SecTermRegistLoader  extends CoreAbstEntityLoader<SecTermRegist> {

	@Inject
	private SecTermRegistEJB ejb;
	@Inject
	private SecTermRegistLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private SecTermRegistLoader loader;	

	@Override
	protected SecTermRegist newObject() {
		return new SecTermRegist();
	}

	@Override
	protected CoreAbstIdentifLookup<SecTermRegist> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<SecTermRegist> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<SecTermRegist> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
