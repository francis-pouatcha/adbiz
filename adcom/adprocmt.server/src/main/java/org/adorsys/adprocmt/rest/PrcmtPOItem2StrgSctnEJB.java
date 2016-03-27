package org.adorsys.adprocmt.rest;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.jpa.PrcmtPOItem2StrgSctn;
import org.adorsys.adprocmt.repo.PrcmtPOItem2StrgSctnRepository;

public class PrcmtPOItem2StrgSctnEJB extends CoreAbstIdentifiedEJB<PrcmtPOItem2StrgSctn>{

	@Inject
	private PrcmtPOItem2StrgSctnRepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtPOItem2StrgSctn> getRepo() {
		return repo;
	}

	public void handleCntnrDeletedEvent(@Observes @EntityDeletedEvent PrcmtPOItem poItem){
		deleteByCntnrIdentif(poItem.getIdentif());
	}
}
