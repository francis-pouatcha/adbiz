package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryArchive;
import org.adorsys.adprocmt.jpa.PrcmtDlvryArchive2PO;
import org.adorsys.adprocmt.repo.PrcmtDlvryArchive2PORepository;

@Stateless
public class PrcmtDlvryArchive2POEJB extends CoreAbstIdentifiedEJB<PrcmtDlvryArchive2PO>{

	@Inject
	private PrcmtDlvryArchive2PORepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvryArchive2PO> getRepo() {
		return repo;
	}

	public void handleCntnrDeletedEvent(@Observes @EntityDeletedEvent PrcmtDeliveryArchive delivery){
		deleteByCntnrIdentif(delivery.getIdentif());
	}
}
