package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryLookup;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesHstry;
import org.adorsys.adcshdwr.repo.CdrDrctSalesHstryRepo;

@Stateless
public class CdrDrctSalesHstryLookup extends CoreAbstBsnsObjectHstryLookup<CdrDrctSalesHstry>{

	@Inject
	private CdrDrctSalesHstryRepo repository;

	@Override
	protected CoreAbstBsnsObjHstryRepo<CdrDrctSalesHstry> getRepo() {
		return repository;
	}
}
