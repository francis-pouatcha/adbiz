package org.adorsys.adcost.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcost.jpa.CstBearer;
import org.adorsys.adcost.repo.CstBearerRepo;

@Stateless
public class CstBearerLookup extends CoreAbstIdentifLookup<CstBearer> {
	@Inject
	private CstBearerRepo repo;
	
	@Override
	protected CoreAbstIdentifRepo<CstBearer> getRepo() {
		return repo;
	}

	@Override
	protected Class<CstBearer> getEntityClass() {
		return CstBearer.class;
	}
}
