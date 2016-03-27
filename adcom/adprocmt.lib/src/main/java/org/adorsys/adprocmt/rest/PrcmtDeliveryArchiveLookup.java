package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryArchive;
import org.adorsys.adprocmt.repo.PrcmtDeliveryArchiveRepository;

@Stateless
public class PrcmtDeliveryArchiveLookup extends CoreAbstBsnsObjectLookup<PrcmtDeliveryArchive>{

	@Inject
	private PrcmtDeliveryArchiveRepository repository;

	@Override
	protected CoreAbstBsnsObjectRepo<PrcmtDeliveryArchive> getBsnsRepo() {
		return repository;
	}

	@Override
	protected Class<PrcmtDeliveryArchive> getEntityClass() {
		return PrcmtDeliveryArchive.class;
	}

	
}
