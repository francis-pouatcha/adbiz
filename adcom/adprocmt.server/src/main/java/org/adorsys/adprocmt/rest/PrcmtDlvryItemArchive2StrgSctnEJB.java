package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemArchive;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemArchive2StrgSctn;
import org.adorsys.adprocmt.repo.PrcmtDlvryItemArchive2StrgSctnRepository;

@Stateless
public class PrcmtDlvryItemArchive2StrgSctnEJB extends CoreAbstIdentifiedEJB<PrcmtDlvryItemArchive2StrgSctn>{

	@Inject
	private PrcmtDlvryItemArchive2StrgSctnRepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvryItemArchive2StrgSctn> getRepo() {
		return repo;
	}

	public void handleCntnrDeletedEvent(@Observes @EntityDeletedEvent PrcmtDlvryItemArchive dlvryItem){
		deleteByCntnrIdentif(dlvryItem.getIdentif());
	}
}
