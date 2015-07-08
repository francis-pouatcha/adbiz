package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.repo.StkArticleLotRepository;

@Stateless
public class StkArticleLotLookup extends CoreAbstBsnsItemLookup<StkArticleLot>{

	@Inject
	private StkArticleLotRepository repository;
	@Inject
	private EntityManager em;

	@Override
	protected CoreAbstBsnsItemRepo<StkArticleLot> getBsnsRepo() {
		return repository;
	}

	@Override
	protected Class<StkArticleLot> getBsnsObjClass() {
		return StkArticleLot.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
}
