package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.repo.PrcmtProcOrderRepository;

@Stateless
public class PrcmtProcOrderLookup extends CoreAbstBsnsObjectLookup<PrcmtProcOrder>{

	@Inject
	private PrcmtProcOrderRepository repository;

	@Override
	protected CoreAbstBsnsObjectRepo<PrcmtProcOrder> getBsnsRepo() {
		return repository;
	}

	@Override
	protected Class<PrcmtProcOrder> getEntityClass() {
		return PrcmtProcOrder.class;
	}

	
}
