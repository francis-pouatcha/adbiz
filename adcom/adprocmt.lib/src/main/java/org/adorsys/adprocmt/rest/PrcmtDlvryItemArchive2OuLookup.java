package org.adorsys.adprocmt.rest;

import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemArchive2Ou;
import org.adorsys.adprocmt.repo.PrcmtDlvryItemArchive2OuRepository;

public class PrcmtDlvryItemArchive2OuLookup extends CoreAbstIdentifLookup<PrcmtDlvryItemArchive2Ou>{

	@Inject
	private PrcmtDlvryItemArchive2OuRepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvryItemArchive2Ou> getRepo() {
		return repo;
	}

	@Override
	protected Class<PrcmtDlvryItemArchive2Ou> getEntityClass() {
		return PrcmtDlvryItemArchive2Ou.class;
	}

	public PrcmtDlvryItemArchive2Ou findByCntnrIdentifAndRcvngOrgUnit(String cntnrIdentif, String rcvngOrgUnit) {
		return repo.findByCntnrIdentifAndRcvngOrgUnit(cntnrIdentif, rcvngOrgUnit).getOptionalResult();
	}

}
