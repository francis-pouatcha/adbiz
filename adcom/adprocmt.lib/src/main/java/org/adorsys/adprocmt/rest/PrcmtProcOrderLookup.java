package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.repo.PrcmtProcOrderRepository;

@Stateless
public class PrcmtProcOrderLookup extends CoreAbstBsnsObjectLookup<PrcmtProcOrder>{

	@Inject
	private PrcmtProcOrderRepository repository;

	@Inject
	private EntityManager em ;

	@Override
	protected CoreAbstBsnsObjectRepo<PrcmtProcOrder> getBsnsRepo() {
		return repository;
	}

	@Override
	protected Class<PrcmtProcOrder> getBsnsObjClass() {
		return PrcmtProcOrder.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	
}
