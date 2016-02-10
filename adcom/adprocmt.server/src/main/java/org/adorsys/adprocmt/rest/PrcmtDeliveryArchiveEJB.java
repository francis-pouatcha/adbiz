package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryArchive;
import org.adorsys.adprocmt.repo.PrcmtDeliveryArchiveRepository;

@Stateless
public class PrcmtDeliveryArchiveEJB extends CoreAbstIdentifiedEJB<PrcmtDeliveryArchive> {

	@Inject
	private PrcmtDeliveryArchiveRepository repo;

	@Override
	protected CoreAbstIdentifRepo<PrcmtDeliveryArchive> getRepo() {
		return repo;
	}
}
