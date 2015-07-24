package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.repo.StkArticleLotRepository;

@Stateless
public class StkArticleLotLookup extends CoreAbstBsnsItemLookup<StkArticleLot>{

	@Inject
	private StkArticleLotRepository repository;

	@Override
	protected CoreAbstBsnsItemRepo<StkArticleLot> getBsnsRepo() {
		return repository;
	}

	@Override
	protected Class<StkArticleLot> getEntityClass() {
		return StkArticleLot.class;
	}
}
