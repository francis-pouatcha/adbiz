package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adprocmt.jpa.PrcmtDlvryArchive2Ou;
import org.adorsys.adprocmt.repo.PrcmtDlvryArchive2OuRepository;

@Stateless
public class PrcmtDlvryArchive2OuLookup extends CoreAbstIdentifLookup<PrcmtDlvryArchive2Ou>{

	@Inject
	private PrcmtDlvryArchive2OuRepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvryArchive2Ou> getRepo() {
		return repo;
	}

	@Override
	protected Class<PrcmtDlvryArchive2Ou> getEntityClass() {
		return PrcmtDlvryArchive2Ou.class;
	}

}
