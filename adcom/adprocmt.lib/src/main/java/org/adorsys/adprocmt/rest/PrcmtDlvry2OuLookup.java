package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2Ou;
import org.adorsys.adprocmt.repo.PrcmtDlvry2OuRepository;

@Stateless
public class PrcmtDlvry2OuLookup extends CoreAbstIdentifLookup<PrcmtDlvry2Ou>{

	@Inject
	private PrcmtDlvry2OuRepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvry2Ou> getRepo() {
		return repo;
	}

	@Override
	protected Class<PrcmtDlvry2Ou> getEntityClass() {
		return PrcmtDlvry2Ou.class;
	}

}
