package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.repo.CatalArticleRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.apache.deltaspike.data.api.QueryResult;

@Stateless
public class CatalArticleEJB  extends
CoreAbstIdentifiedEJB<CatalArticle> {

	@Inject
	private CatalArticleRepository repository;

	@Override
	protected CoreAbstIdentifRepo<CatalArticle> getRepo() {
		return repository;
	}
	
	public QueryResult<CatalArticle> findByArtNAme(String artName){
		return repository.findByArtName(artName);
	}
	
	public  Long countByArtNAme(String artName){
		return repository.countByArtName(artName);
	}
}

