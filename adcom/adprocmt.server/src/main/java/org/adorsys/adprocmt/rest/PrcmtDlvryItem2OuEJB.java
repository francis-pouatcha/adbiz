package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2Ou;
import org.adorsys.adprocmt.repo.PrcmtDlvryItem2OuRepository;

@Stateless
public class PrcmtDlvryItem2OuEJB extends CoreAbstIdentifiedEJB<PrcmtDlvryItem2Ou>{

	@Inject
	private PrcmtDlvryItem2OuRepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvryItem2Ou> getRepo() {
		return repo;
	}

	public void handleCntnrDeletedEvent(@Observes @EntityDeletedEvent PrcmtDlvryItem dlvryItem){
		deleteByCntnrIdentif(dlvryItem.getIdentif());
	}
}
