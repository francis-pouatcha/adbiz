package org.adorsys.adprocmt.rest;

import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2Ou;
import org.adorsys.adprocmt.repo.PrcmtDlvry2OuRepository;

public class PrcmtDlvry2OuEJB extends CoreAbstIdentifiedEJB<PrcmtDlvry2Ou>{

	@Inject
	private PrcmtDlvry2OuRepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvry2Ou> getRepo() {
		return repo;
	}
}
