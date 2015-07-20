package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.repo.CatalArticleRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;

@Stateless
public class CatalArticleLookup extends CatalAbstractArticleLookup<CatalArticle>{

	@Inject
	private CatalArticleRepository repository;

	@Override
	protected CoreAbstIdentifRepo<CatalArticle> getRepo() {
		return repository;
	}

	@Override
	protected Class<CatalArticle> getEntityClass() {
		return CatalArticle.class;
	}
}
