package org.adorsys.adprocmt.rest;

import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2POItem;
import org.adorsys.adprocmt.repo.PrcmtDlvryItem2POItemRepository;

public class PrcmtDlvryItem2POItemEJB extends CoreAbstIdentifiedEJB<PrcmtDlvryItem2POItem>{

	@Inject
	private PrcmtDlvryItem2POItemRepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvryItem2POItem> getRepo() {
		return repo;
	}
}
