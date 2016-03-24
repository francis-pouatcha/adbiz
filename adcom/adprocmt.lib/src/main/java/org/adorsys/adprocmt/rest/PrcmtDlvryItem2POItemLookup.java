package org.adorsys.adprocmt.rest;

import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2POItem;
import org.adorsys.adprocmt.repo.PrcmtDlvryItem2POItemRepository;

public class PrcmtDlvryItem2POItemLookup extends CoreAbstIdentifLookup<PrcmtDlvryItem2POItem>{

	@Inject
	private PrcmtDlvryItem2POItemRepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvryItem2POItem> getRepo() {
		return repo;
	}

	@Override
	protected Class<PrcmtDlvryItem2POItem> getEntityClass() {
		return PrcmtDlvryItem2POItem.class;
	}

}
