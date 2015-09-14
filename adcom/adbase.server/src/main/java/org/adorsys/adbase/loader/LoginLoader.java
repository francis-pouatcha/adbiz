package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.rest.LoginEJB;
import org.adorsys.adbase.rest.LoginLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class LoginLoader  extends CoreAbstEntityLoader<Login> {

	@Inject
	private LoginEJB ejb;
	@Inject
	private LoginLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private LoginLoader loader;	

	@Override
	protected Login newObject() {
		return new Login();
	}

	@Override
	protected CoreAbstIdentifLookup<Login> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<Login> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<Login> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
