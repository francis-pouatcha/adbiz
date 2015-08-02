package org.adorsys.adinvtry.rest;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectEJB;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryCstr;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryType;
import org.adorsys.adinvtry.jpa.InvJob;
import org.adorsys.adinvtry.jpa.InvStep;
import org.adorsys.adinvtry.repo.InvInvtryRepository;
import org.adorsys.adinvtry.task.InvInvtryPrepareSplitterTask;

@Stateless
public class InvInvtryEJB extends CoreAbstBsnsObjectEJB<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr>
{

	@Inject
	private InvInvtryRepository repository;
	@Inject
	private InvInvtryInjector injector;
	@Inject
	private InvInvtryPrepareSplitterTask splitterTaskp;

	@Override
	protected CoreAbstBsnsObjInjector<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr> getInjector() {
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
	
	public void prepareInvtry(String identif){
		// check existence of entity
		Long entCount = injector.getBsnsObjLookup().countByIdentif(identif);
		if(entCount<=0) return;
		
		// Quit if there is any item.
		Long itemCount = injector.getItemLookup().countByCntnrIdentif(identif);
		if(itemCount>0) return;
		
		splitterTaskp.createPrepareJob(identif);
	}
	
	

}
