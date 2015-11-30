package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityCstrEJB;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryCstr;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.repo.PrcmtDeliveryCstrRepository;

@Stateless
public class PrcmtDeliveryCstrEJB extends CoreAbstEntityCstrEJB<PrcmtDeliveryCstr>{

	@Inject
	private PrcmtDeliveryCstrRepository repo;
	
	@Override
	public PrcmtDeliveryCstr newCsrInstance() {
		return new PrcmtDeliveryCstr();
	}

	@Override
	protected CoreAbstIdentifRepo<PrcmtDeliveryCstr> getRepo() {
		return repo;
	}
	
	/**
	 * Delete constraint on deletion of the containing entity.
	 * 
	 * @param delivery
	 */
	public void handleDlvryDeletedEvent(@Observes @EntityDeletedEvent PrcmtDelivery delivery){
		deleteByCntnrIdentif(delivery.getIdentif());
	}
	
	/**
	 * Delete constraint on deletion of the containing entity.
	 * 
	 * @param dlvryItem
	 */
	public void handleDlvryDeletedEvent(@Observes @EntityDeletedEvent PrcmtDlvryItem dlvryItem){
		deleteByCntnrIdentif(dlvryItem.getIdentif());
	}
}
