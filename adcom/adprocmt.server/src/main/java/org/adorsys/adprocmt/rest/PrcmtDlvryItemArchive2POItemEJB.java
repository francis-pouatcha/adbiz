package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemArchive;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemArchive2POItem;
import org.adorsys.adprocmt.repo.PrcmtDlvryItemArchive2POItemRepository;

@Stateless
public class PrcmtDlvryItemArchive2POItemEJB extends CoreAbstIdentifiedEJB<PrcmtDlvryItemArchive2POItem>{

	@Inject
	private PrcmtDlvryItemArchive2POItemRepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvryItemArchive2POItem> getRepo() {
		return repo;
	}
	
	public void handleCntnrDeletedEvent(@Observes @EntityDeletedEvent PrcmtDlvryItemArchive dlvryItem){
		deleteByCntnrIdentif(dlvryItem.getIdentif());
	}
}
