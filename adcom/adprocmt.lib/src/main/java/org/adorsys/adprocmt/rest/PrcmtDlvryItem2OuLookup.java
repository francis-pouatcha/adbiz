package org.adorsys.adprocmt.rest;

import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2Ou;
import org.adorsys.adprocmt.repo.PrcmtDlvryItem2OuRepository;

public class PrcmtDlvryItem2OuLookup extends CoreAbstIdentifLookup<PrcmtDlvryItem2Ou>{

	@Inject
	private PrcmtDlvryItem2OuRepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvryItem2Ou> getRepo() {
		return repo;
	}

	@Override
	protected Class<PrcmtDlvryItem2Ou> getEntityClass() {
		return PrcmtDlvryItem2Ou.class;
	}

}
