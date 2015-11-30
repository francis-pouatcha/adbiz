package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityCstrEJB;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderCstr;
import org.adorsys.adprocmt.repo.PrcmtProcOrderCstrRepository;

@Stateless
public class PrcmtProcOrderCstrEJB extends CoreAbstEntityCstrEJB<PrcmtProcOrderCstr>{

	@Inject
	private PrcmtProcOrderCstrRepository repo;
	
	@Override
	public PrcmtProcOrderCstr newCsrInstance() {
		return new PrcmtProcOrderCstr();
	}

	@Override
	protected CoreAbstIdentifRepo<PrcmtProcOrderCstr> getRepo() {
		return repo;
	}

	/**
	 * Delete constraint on deletion of the containing entity.
	 * 
	 * @param procOrder
	 */
	public void handleDeletedEvent(@Observes @EntityDeletedEvent PrcmtProcOrder procOrder){
		deleteByCntnrIdentif(procOrder.getIdentif());
	}

	/**
	 * Delete constraint on deletion of the containing entity.
	 * 
	 * @param poItem
	 */
	public void handleDeletedEvent(@Observes @EntityDeletedEvent PrcmtPOItem poItem){
		deleteByCntnrIdentif(poItem.getIdentif());
	}
}
