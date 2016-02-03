package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryCstr;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvJob;
import org.adorsys.adinvtry.jpa.InvStep;
import org.adorsys.adinvtry.repo.InvInvtryItemRepository;

@Stateless
public class InvInvtryItemEJB extends CoreAbstBsnsItemEJB<InvInvtry, InvInvtryItem, InvInvtryHstry, 
	InvJob, InvStep, InvInvtryCstr>
{

	@Inject
	private InvInvtryItemRepository repository;
	@Inject
	private InvInvtryInjector injector;

	@Override
	protected CoreAbstBsnsObjInjector<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr> getInjector() {
		return injector;
	}

	@Override
	protected CoreAbstBsnsItemRepo<InvInvtryItem> getBsnsRepo() {
		return repository;
	}
}
