package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adcore.enums.CoreProcStepEnum;
import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adstock.jpa.StkMvnt;
import org.adorsys.adstock.jpa.StkMvntHstryGtwy;
import org.adorsys.adstock.repo.StkMvntHstryGtwyRepo;
import org.adorsys.adstock.repo.StkMvntRepository;

@Stateless
public class StkMvntListener {

	@Inject
	private StkMvntRepository repository;
	
	@Inject
	private StkMvntHstryGtwyRepo hstryRepo;

	/**
	 * Create the stock mvnt and the corresponding history object.
	 * 
	 */
	public StkMvnt fireNewMvntEvent(StkMvnt stkMvnt, CoreAbstBsnsObjectHstry hstry){
		
		repository.persist(stkMvnt);
		
		StkMvntHstryGtwy h = new StkMvntHstryGtwy();
		hstry.copyTo(h);
		h.setEntIdentif(stkMvnt.getIdentif());
		h.setItemIdentif(stkMvnt.getOrigProcsNbr());
		h.setEntStatus(CoreProcessStatusEnum.CREATED.name());
		h.setHstryType(CoreHistoryTypeEnum.INITIATED.name());
		h.setProcStep(CoreProcStepEnum.INITIATING.name());
		h = hstryRepo.save(h);
		
		return stkMvnt;
	}
}
