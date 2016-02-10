package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemArchive;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemArchive2Ou;
import org.adorsys.adprocmt.repo.PrcmtDlvryItemArchive2OuRepository;

@Stateless
public class PrcmtDlvryItemArchive2OuEJB extends CoreAbstIdentifiedEJB<PrcmtDlvryItemArchive2Ou>{

	@Inject
	private PrcmtDlvryItemArchive2OuRepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvryItemArchive2Ou> getRepo() {
		return repo;
	}

	public void handleCntnrDeletedEvent(@Observes @EntityDeletedEvent PrcmtDlvryItemArchive dlvryItem){
		deleteByCntnrIdentif(dlvryItem.getIdentif());
	}
}
