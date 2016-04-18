package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItem;
import org.adorsys.adcshdwr.repo.CdrDrctSalesItemRepo;

@Stateless
public class CdrDrctSalesItemLookup extends CoreAbstBsnsItemLookup<CdrDrctSalesItem>{

	@Inject
	private CdrDrctSalesItemRepo repository;

	@Override
	protected CoreAbstBsnsItemRepo<CdrDrctSalesItem> getBsnsRepo() {
		return repository;
	}

	@Override
	protected Class<CdrDrctSalesItem> getEntityClass() {
		return CdrDrctSalesItem.class;
	}
}
