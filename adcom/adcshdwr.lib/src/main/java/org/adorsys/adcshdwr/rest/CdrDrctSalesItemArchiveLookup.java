package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItemArchive;
import org.adorsys.adcshdwr.repo.CdrDrctSalesItemArchiveRepo;

@Stateless
public class CdrDrctSalesItemArchiveLookup extends CoreAbstBsnsItemLookup<CdrDrctSalesItemArchive>{

	@Inject
	private CdrDrctSalesItemArchiveRepo repository;

	@Override
	protected CoreAbstBsnsItemRepo<CdrDrctSalesItemArchive> getBsnsRepo() {
		return repository;
	}

	@Override
	protected Class<CdrDrctSalesItemArchive> getEntityClass() {
		return CdrDrctSalesItemArchive.class;
	}
}
