package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.repo.CdrDrctSalesRepo;

@Stateless
public class CdrDrctSalesLookup extends CoreAbstBsnsObjectLookup<CdrDrctSales>
{

	@Inject
	private CdrDrctSalesRepo repository;

	@Override
	protected CoreAbstBsnsObjectRepo<CdrDrctSales> getBsnsRepo() {
		return repository;
	}

	@Override
	protected Class<CdrDrctSales> getEntityClass() {
		return CdrDrctSales.class;
	}
}
