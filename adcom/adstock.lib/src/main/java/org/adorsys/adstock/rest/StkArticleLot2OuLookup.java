package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adstock.jpa.StkArticleLot2Ou;
import org.adorsys.adstock.repo.StkArticleLot2OuRepository;

@Stateless
public class StkArticleLot2OuLookup extends
		CoreAbstIdentifLookup<StkArticleLot2Ou> {

	@Inject
	private StkArticleLot2OuRepository repository;

	@Override
	protected CoreAbstIdentifRepo<StkArticleLot2Ou> getRepo() {
		return repository;
	}

	@Override
	protected Class<StkArticleLot2Ou> getEntityClass() {
		return StkArticleLot2Ou.class;
	}
}
