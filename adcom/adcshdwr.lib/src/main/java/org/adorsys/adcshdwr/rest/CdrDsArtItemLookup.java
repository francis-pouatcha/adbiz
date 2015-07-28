package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.adorsys.adcshdwr.repo.CdrDsArtItemRepo;

@Stateless
public class CdrDsArtItemLookup extends CoreAbstBsnsItemLookup<CdrDsArtItem>{

	@Inject
	private CdrDsArtItemRepo repository;
	
	@Inject
	private EntityManager em;

	@Override
	protected CoreAbstBsnsItemRepo<CdrDsArtItem> getBsnsRepo() {
		return repository;
	}

	@Override
	protected Class<CdrDsArtItem> getBsnsObjClass() {
		return CdrDsArtItem.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
