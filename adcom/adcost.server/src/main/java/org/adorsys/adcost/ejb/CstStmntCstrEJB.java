package org.adorsys.adcost.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityCstrEJB;
import org.adorsys.adcost.jpa.CstStmntCstr;
import org.adorsys.adcost.repo.CstStmntCstrRepo;

@Stateless
public class CstStmntCstrEJB extends CoreAbstEntityCstrEJB<CstStmntCstr> {

	@Inject
	private CstStmntCstrRepo repository;
	
	@Override
	public CstStmntCstr newCsrInstance() {
		return new CstStmntCstr();
	}

	@Override
	protected CoreAbstIdentifRepo<CstStmntCstr> getRepo() {
		return repository;
	}

}
