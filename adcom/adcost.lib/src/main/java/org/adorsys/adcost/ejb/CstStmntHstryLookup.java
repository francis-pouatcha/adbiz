package org.adorsys.adcost.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryLookup;
import org.adorsys.adcost.jpa.CstStmntHstry;
import org.adorsys.adcost.repo.CstStmntHstryRepo;

@Stateless
public class CstStmntHstryLookup extends CoreAbstBsnsObjectHstryLookup<CstStmntHstry>{

	@Inject
	private CstStmntHstryRepo repository;

	@Override
	protected CstStmntHstryRepo getRepo() {
		return repository;
	}

}
