package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;
import org.adorsys.adcore.rest.CoreAbstArchiveHstryEJB;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstryArchive;
import org.adorsys.adprocmt.repo.PrcmtDeliveryHstryArchiveRepository;

@Stateless
public class PrcmtDeliveryHstryArchiveEJB extends CoreAbstArchiveHstryEJB<PrcmtDeliveryHstryArchive>
{
	@Inject
	private PrcmtDeliveryHstryArchiveRepository repo;
	
	@Override
	protected CoreAbstIdentifObjectHstryRepo<PrcmtDeliveryHstryArchive> getRepo() {
		return repo;
	}
}
