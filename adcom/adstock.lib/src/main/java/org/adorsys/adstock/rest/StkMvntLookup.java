package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adstock.jpa.StkMvnt;
import org.adorsys.adstock.repo.StkMvntRepository;

@Stateless
public class StkMvntLookup extends CoreAbstBsnsItemLookup<StkMvnt> {

	@Inject
	private StkMvntRepository repository;
	@Inject
	private EntityManager em;

	@Override
	protected CoreAbstBsnsItemRepo<StkMvnt> getBsnsRepo() {
		return repository;
	}

	@Override
	protected Class<StkMvnt> getBsnsObjClass() {
		return StkMvnt.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
