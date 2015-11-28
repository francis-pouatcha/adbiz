package org.adorsys.adprocmt.rest;

import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2PO;
import org.adorsys.adprocmt.repo.PrcmtDlvry2PORepository;

public class PrcmtDlvry2POLookup extends CoreAbstIdentifLookup<PrcmtDlvry2PO>{

	@Inject
	private PrcmtDlvry2PORepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvry2PO> getRepo() {
		return repo;
	}

	@Override
	protected Class<PrcmtDlvry2PO> getEntityClass() {
		return PrcmtDlvry2PO.class;
	}

}
