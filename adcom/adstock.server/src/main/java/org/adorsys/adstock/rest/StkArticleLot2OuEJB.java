package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adstock.jpa.StkArticleLot2Ou;
import org.adorsys.adstock.repo.StkArticleLot2OuRepository;

@Stateless
public class StkArticleLot2OuEJB extends
		CoreAbstIdentifiedEJB<StkArticleLot2Ou> {

	@Inject
	private StkArticleLot2OuRepository repository;

	@Override
	protected CoreAbstIdentifRepo<StkArticleLot2Ou> getRepo() {
		return repository;
	}
}
