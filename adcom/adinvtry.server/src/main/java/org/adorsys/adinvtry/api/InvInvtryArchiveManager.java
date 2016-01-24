package org.adorsys.adinvtry.api;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.rest.CoreAbstArchiveManager;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryArchive;
import org.adorsys.adinvtry.jpa.InvInvtryCstr;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryHstryArchive;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryItemArchive;
import org.adorsys.adinvtry.jpa.InvJob;
import org.adorsys.adinvtry.jpa.InvStep;
import org.adorsys.adinvtry.rest.InvInvtryArchiveEJB;
import org.adorsys.adinvtry.rest.InvInvtryInjector;

@Stateless
public class InvInvtryArchiveManager  extends CoreAbstArchiveManager<InvInvtry, InvInvtryArchive, InvInvtryItem, InvInvtryItemArchive, InvInvtryHstry, InvInvtryHstryArchive, 
	InvJob, InvStep, InvInvtryCstr> {
	
	@Inject
	private InvInvtryInjector injector;
	@Inject
	private InvInvtryArchiveEJB ejb;
	
	@Override
	protected CoreAbstBsnsObjInjector<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr> getInjector() {
		return injector;
	}

	@Override
	protected CoreAbstIdentifiedEJB<InvInvtryArchive> getArchiveObjEjb() {
		return ejb;
	}

	@Override
	protected InvInvtryArchive newArchiveObject() {
		return new InvInvtryArchive();
	}
}
