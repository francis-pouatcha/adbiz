package org.adorsys.adprocmt.rest;

import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adprocmt.jpa.PrcmtDlvryArchive2PO;
import org.adorsys.adprocmt.repo.PrcmtDlvryArchive2PORepository;

public class PrcmtDlvryArchive2POLookup extends CoreAbstIdentifLookup<PrcmtDlvryArchive2PO>{

	@Inject
	private PrcmtDlvryArchive2PORepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvryArchive2PO> getRepo() {
		return repo;
	}

	@Override
	protected Class<PrcmtDlvryArchive2PO> getEntityClass() {
		return PrcmtDlvryArchive2PO.class;
	}

}
