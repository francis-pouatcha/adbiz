package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryLookup;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesHstryArchive;
import org.adorsys.adcshdwr.repo.CdrDrctSalesHstryArchiveRepo;

@Stateless
public class CdrDrctSalesHstryArchiveLookup extends CoreAbstBsnsObjectHstryLookup<CdrDrctSalesHstryArchive>{

	@Inject
	private CdrDrctSalesHstryArchiveRepo repository;

	@Override
	protected CoreAbstBsnsObjHstryRepo<CdrDrctSalesHstryArchive> getRepo() {
		return repository;
	}
}
