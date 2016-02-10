package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryLookup;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstryArchive;
import org.adorsys.adprocmt.repo.PrcmtDeliveryHstryArchiveRepository;

@Stateless
public class PrcmtDeliveryHstryArchiveLookup extends CoreAbstBsnsObjectHstryLookup<PrcmtDeliveryHstryArchive>{

	@Inject
	private PrcmtDeliveryHstryArchiveRepository repository;

	@Override
	protected CoreAbstBsnsObjHstryRepo<PrcmtDeliveryHstryArchive> getRepo() {
		return repository;
	}
}
