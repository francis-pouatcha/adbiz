package org.adorsys.adprocmt.rest;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2PO;
import org.adorsys.adprocmt.repo.PrcmtDlvry2PORepository;

public class PrcmtDlvry2POEJB extends CoreAbstIdentifiedEJB<PrcmtDlvry2PO>{

	@Inject
	private PrcmtDlvry2PORepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvry2PO> getRepo() {
		return repo;
	}

	public void handleCntnrDeletedEvent(@Observes @EntityDeletedEvent PrcmtDelivery delivery){
		deleteByCntnrIdentif(delivery.getIdentif());
	}
}
