package org.adorsys.adprocmt.rest;

import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adprocmt.jpa.PrcmtDlvryArtPrcssng;
import org.adorsys.adprocmt.repo.PrcmtDlvryArtPrcssngRepository;

public class PrcmtDlvryArtPrcssngLookup extends CoreAbstIdentifLookup<PrcmtDlvryArtPrcssng>{

	@Inject
	private PrcmtDlvryArtPrcssngRepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvryArtPrcssng> getRepo() {
		return repo;
	}

	@Override
	protected Class<PrcmtDlvryArtPrcssng> getEntityClass() {
		return PrcmtDlvryArtPrcssng.class;
	}

}
