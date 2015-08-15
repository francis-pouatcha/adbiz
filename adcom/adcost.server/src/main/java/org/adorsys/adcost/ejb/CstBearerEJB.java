package org.adorsys.adcost.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcost.jpa.CstBearer;
import org.adorsys.adcost.repo.CstBearerRepo;

@Stateless
public class CstBearerEJB extends CoreAbstIdentifiedEJB<CstBearer> {

	@Inject
	private CstBearerRepo repo;

	@Override
	protected CoreAbstIdentifRepo<CstBearer> getRepo() {
		return repo;
	}

}
