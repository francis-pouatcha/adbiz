package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctn;
import org.adorsys.adprocmt.repo.PrcmtDlvryItem2StrgSctnRepository;

@Stateless
public class PrcmtDlvryItem2StrgSctnEJB extends CoreAbstIdentifiedEJB<PrcmtDlvryItem2StrgSctn>{

	@Inject
	private PrcmtDlvryItem2StrgSctnRepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvryItem2StrgSctn> getRepo() {
		return repo;
	}

	public void handleCntnrDeletedEvent(@Observes @EntityDeletedEvent PrcmtDlvryItem dlvryItem){
		deleteByCntnrIdentif(dlvryItem.getIdentif());
	}
}
