package org.adorsys.adcshdwr.rest;

import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstEntityCstrEJB;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesCstr;
import org.adorsys.adcshdwr.repo.CdrDrctSalesCstrRepo;

public class CdrDrctSalesCstrEJB extends CoreAbstEntityCstrEJB<CdrDrctSalesCstr> {

	@Inject
	private CdrDrctSalesCstrRepo repo;
	@Override
	public CdrDrctSalesCstr newCsrInstance() {
		return new CdrDrctSalesCstr();
	}

	@Override
	protected CoreAbstIdentifDataRepo<CdrDrctSalesCstr> getRepo() {
		return repo;
	}

}
