package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryArchive;
import org.adorsys.adprocmt.jpa.PrcmtDlvryArchive2Ou;
import org.adorsys.adprocmt.repo.PrcmtDlvryArchive2OuRepository;

@Stateless
public class PrcmtDlvryArchive2OuEJB extends CoreAbstIdentifiedEJB<PrcmtDlvryArchive2Ou>{

	@Inject
	private PrcmtDlvryArchive2OuRepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvryArchive2Ou> getRepo() {
		return repo;
	}
	
	public void handleCntnrDeletedEvent(@Observes @EntityDeletedEvent PrcmtDeliveryArchive delivery){
		deleteByCntnrIdentif(delivery.getIdentif());
	}
}
