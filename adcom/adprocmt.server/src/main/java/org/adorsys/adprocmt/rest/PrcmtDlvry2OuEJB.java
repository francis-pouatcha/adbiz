package org.adorsys.adprocmt.rest;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2Ou;
import org.adorsys.adprocmt.repo.PrcmtDlvry2OuRepository;

public class PrcmtDlvry2OuEJB extends CoreAbstIdentifiedEJB<PrcmtDlvry2Ou>{

	@Inject
	private PrcmtDlvry2OuRepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvry2Ou> getRepo() {
		return repo;
	}
	
	public void handleCntnrDeletedEvent(@Observes @EntityDeletedEvent PrcmtDelivery delivery){
		deleteByCntnrIdentif(delivery.getIdentif());
	}
}
