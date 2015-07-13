package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;
import org.adorsys.adstock.jpa.StkArticleLot2Ou;
import org.adorsys.adstock.repo.StkArticleLot2OuRepository;

@Stateless
public class StkArticleLot2OuLookup extends
		CoreAbstIdentifiedLookup<StkArticleLot2Ou> {

	@Inject
	private StkArticleLot2OuRepository repository;

	@Override
	protected CoreAbstIdentifDataRepo<StkArticleLot2Ou> getRepo() {
		return repository;
	}
}
