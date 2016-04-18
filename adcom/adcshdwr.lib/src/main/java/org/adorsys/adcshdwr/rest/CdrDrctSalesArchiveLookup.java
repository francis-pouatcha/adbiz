package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesArchive;
import org.adorsys.adcshdwr.repo.CdrDrctSalesArchiveRepo;

@Stateless
public class CdrDrctSalesArchiveLookup extends CoreAbstBsnsObjectLookup<CdrDrctSalesArchive>
{

	@Inject
	private CdrDrctSalesArchiveRepo repository;

	@Override
	protected CoreAbstBsnsObjectRepo<CdrDrctSalesArchive> getBsnsRepo() {
		return repository;
	}

	@Override
	protected Class<CdrDrctSalesArchive> getEntityClass() {
		return CdrDrctSalesArchive.class;
	}
}
