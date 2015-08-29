package org.adorsys.adbase.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OrgContact;
import org.adorsys.adbase.rest.OrgContactEJB;
import org.adorsys.adbase.rest.OrgContactLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class OrgContactLoader  extends CoreAbstEntityLoader<OrgContact> {

	@Inject
	private OrgContactEJB ejb;
	@Inject
	private OrgContactLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private OrgContactLoader loader;	

	@Override
	protected OrgContact newObject() {
		return new OrgContact();
	}

	@Override
	protected CoreAbstIdentifLookup<OrgContact> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<OrgContact> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstLoader<OrgContact> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

}
