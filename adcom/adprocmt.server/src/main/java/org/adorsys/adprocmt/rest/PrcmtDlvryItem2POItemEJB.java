package org.adorsys.adprocmt.rest;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2POItem;
import org.adorsys.adprocmt.repo.PrcmtDlvryItem2POItemRepository;

public class PrcmtDlvryItem2POItemEJB extends CoreAbstIdentifiedEJB<PrcmtDlvryItem2POItem>{

	@Inject
	private PrcmtDlvryItem2POItemRepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvryItem2POItem> getRepo() {
		return repo;
	}
	
	public void handleCntnrDeletedEvent(@Observes @EntityDeletedEvent PrcmtDlvryItem dlvryItem){
		deleteByCntnrIdentif(dlvryItem.getIdentif());
	}
}
