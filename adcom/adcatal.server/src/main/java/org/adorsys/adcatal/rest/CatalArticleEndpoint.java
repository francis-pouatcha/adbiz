package org.adorsys.adcatal.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.jpa.CatalArticleSearchInput;
import org.adorsys.adcatal.jpa.CatalArticleSearchResult;
import org.adorsys.adcatal.jpa.CatalArticle_;
import org.adorsys.adcatal.loader.CatalLoaderRegistration;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcore.xls.CoreAbstLoaderRegistration;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

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
	
	
}