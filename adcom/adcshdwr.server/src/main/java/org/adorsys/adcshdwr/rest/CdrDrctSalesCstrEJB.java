package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityCstrRepo;
import org.adorsys.adcore.rest.CoreAbstEntityCstrEJB;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesCstr;
import org.adorsys.adcshdwr.repo.CdrDrctSalesCstrRepo;

@Stateless
public class CdrDrctSalesCstrEJB extends CoreAbstEntityCstrEJB<CdrDrctSalesCstr> {

	@Inject
	private CdrDrctSalesCstrRepo repo;
	@Override
	public CdrDrctSalesCstr newCsrInstance() {
		return new CdrDrctSalesCstr();
	}

	@Override
	protected CoreAbstEntityCstrRepo<CdrDrctSalesCstr> getRepo() {
		return repo;
	}

}
