package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityCstrRepo;
import org.adorsys.adcore.rest.CoreAbstEntityCstrLookup;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesCstr;
import org.adorsys.adcshdwr.repo.CdrDrctSalesCstrRepo;

@Stateless
public class CdrDrctSalesCstrLookup extends CoreAbstEntityCstrLookup<CdrDrctSalesCstr> {

	@Inject
	private CdrDrctSalesCstrRepo repository;

	@Override
	protected CoreAbstEntityCstrRepo<CdrDrctSalesCstr> getCstrRepo() {
		return repository;
	}

	@Override
	protected Class<CdrDrctSalesCstr> getEntityClass() {
		return CdrDrctSalesCstr.class;
	}
}
