package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectEJB;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryCstr;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryJob;
import org.adorsys.adinvtry.jpa.InvInvtryStep;
import org.adorsys.adinvtry.jpa.InvInvtryType;
import org.adorsys.adinvtry.repo.InvInvtryRepository;

@Stateless
public class InvInvtryEJB extends CoreAbstBsnsObjectEJB<InvInvtry, InvInvtryItem, InvInvtryHstry, InvInvtryJob, InvInvtryStep, InvInvtryCstr>
{

	@Inject
	private InvInvtryRepository repository;
	@Inject
	private InvInvtryInjector injector;

	@Override
	protected CoreAbstBsnsObjInjector<InvInvtry, InvInvtryItem, InvInvtryHstry, InvInvtryJob, InvInvtryStep, InvInvtryCstr> getInjector() {
		return injector;
	}

	@Override
	protected CoreAbstBsnsObjectRepo<InvInvtry> getBsnsRepo() {
		return repository;
	}

	/*
	 * Override create.
	 * (non-Javadoc)
	 * @see org.adorsys.adcore.rest.CoreAbstBsnsObjectEJB#create(org.adorsys.adcore.jpa.CoreAbstBsnsObject)
	 */
	public InvInvtry create(InvInvtry entity){
		if(entity.getTxType()==null)entity.setTxType(InvInvtryType.FREE_INV.name());
		return super.create(entity);
	}
}
