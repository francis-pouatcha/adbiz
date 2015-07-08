package org.adorsys.adcatal.rest;

import java.lang.reflect.Field;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.jpa.CatalArticle_;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;

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

	@Override
	protected CoreAbstIdentifiedLookup<CatalArticle> getLookup() {
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

}