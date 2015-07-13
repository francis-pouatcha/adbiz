package org.adorsys.adstock.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEndpoint;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adstock.jpa.StkArticleLotSearchInput;
import org.adorsys.adstock.jpa.StkArticleLotSearchResult;
import org.adorsys.adstock.jpa.StkMvnt;
import org.adorsys.adstock.jpa.StkMvntSearchInput;
import org.adorsys.adstock.jpa.StkMvntSearchResult;
import org.adorsys.adstock.jpa.StkMvnt_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/stkmvnts")
public class StkMvntEndpoint
		extends
		CoreAbstBsnsItemEndpoint<StkMvnt, CoreAbstBsnsItemSearchInput<StkMvnt>, CoreAbstBsnsItemSearchResult<StkMvnt, CoreAbstBsnsItemSearchInput<StkMvnt>>> {

	@Inject
	private StkMvntLookup lookup;

	@Override
	protected CoreAbstBsnsItemLookup<StkMvnt> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return StkMvnt_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<StkMvnt, CoreAbstBsnsItemSearchInput<StkMvnt>> newSearchResult(
			Long size, List<StkMvnt> resultList,
			CoreAbstBsnsItemSearchInput<StkMvnt> searchInput) {
		return new StkMvntSearchResult(size, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsItemSearchInput<StkMvnt> newSearchInput() {
		return new StkMvntSearchInput();
	}
}