package org.adorsys.adcatal.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.jpa.CatalArtLangMapping_;
import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.jpa.CatalArticleSearchInput;
import org.adorsys.adcatal.jpa.CatalArticleSearchResult;
import org.adorsys.adcatal.jpa.CatalArticle_;
import org.adorsys.adcatal.loader.CatalLoaderRegistration;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcore.xls.CoreAbstLoaderRegistration;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalarticles")
public class CatalArticleEndpoint extends
		CoreAbstIdentifiedEndpoint<CatalArticle> {

	@Inject
	private CatalArticleEJB ejb;
	@Inject
	private CatalArticleLookup lookup;
	@Inject
	private CatalLoaderRegistration loaderRegistration;

	@Inject
	private CatalArtLangMappingLookup langMappingLookup;
	
	@Override
	protected CoreAbstIdentifLookup<CatalArticle> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalArticle> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return CatalArticle_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<CatalArticle> newSearchInput() {
		return new CatalArticleSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<CatalArticle> newSearchResult(
			Long count,Long total,List<CatalArticle> resultList,
			CoreAbstIdentifObjectSearchInput<CatalArticle> searchInput) {
		return new CatalArticleSearchResult(count, total, resultList, searchInput);
	}

	@Override
	protected CoreAbstLoaderRegistration getLoaderRegistration() {
		return loaderRegistration;
	}
	

	@GET
	@Path("/findByArtName")
	@Produces({ "application/json"})
	@Consumes({ "application/json"})
	public CoreAbstIdentifObjectSearchResult<CatalArticle> findByArtName(@QueryParam("artName") String artName,
			@QueryParam("start") int start, @QueryParam("max") int max) {
		CatalArtLangMapping entity = new CatalArtLangMapping();
		entity.setArtName(artName);
		SingularAttribute<CatalArtLangMapping, ?>[] attributes = new SingularAttribute[]{CatalArtLangMapping_.artName};
		Long count = langMappingLookup.countByLike(entity, attributes);
		List<CatalArtLangMapping> resultList = langMappingLookup.findByLike(entity, start, max, attributes);
		List<String> identifs = new ArrayList<>();
		for (CatalArtLangMapping catalArtLangMapping : resultList) {
			identifs.add(catalArtLangMapping.getCntnrIdentif());
		}
		List<CatalArticle> foundArticles = getLookup().findByIdentifIn(identifs);
		Long total = langMappingLookup.count();
		CoreAbstIdentifObjectSearchInput<CatalArticle> searchInput = newSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return newSearchResult(count, total,
				detach(foundArticles), detach(searchInput));
	}
	
	

}