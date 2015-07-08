package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.repo.PrcmtPOItemRepository;

@Stateless
public class PrcmtPOItemLookup extends CoreAbstBsnsItemLookup<PrcmtPOItem> {

	@Inject
	private PrcmtPOItemRepository repository;
	@Inject
	private EntityManager em;

	@Override
	protected CoreAbstBsnsItemRepo<PrcmtPOItem> getBsnsRepo() {
		return repository;
	}

	@Override
	protected Class<PrcmtPOItem> getBsnsObjClass() {
		return PrcmtPOItem.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
