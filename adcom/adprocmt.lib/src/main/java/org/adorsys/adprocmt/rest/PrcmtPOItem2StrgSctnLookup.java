package org.adorsys.adprocmt.rest;

import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adprocmt.jpa.PrcmtPOItem2StrgSctn;
import org.adorsys.adprocmt.repo.PrcmtPOItem2StrgSctnRepository;

public class PrcmtPOItem2StrgSctnLookup extends CoreAbstIdentifLookup<PrcmtPOItem2StrgSctn>{

	@Inject
	private PrcmtPOItem2StrgSctnRepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtPOItem2StrgSctn> getRepo() {
		return repo;
	}

	@Override
	protected Class<PrcmtPOItem2StrgSctn> getEntityClass() {
		return PrcmtPOItem2StrgSctn.class;
	}

}
