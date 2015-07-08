package org.adorsys.adcshdwr.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEndpoint;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.adorsys.adcshdwr.jpa.CdrDsArtItemSearchInput;
import org.adorsys.adcshdwr.jpa.CdrDsArtItemSearchResult;
import org.adorsys.adcshdwr.jpa.CdrDsArtItem_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/cdrdsartitems")
public class CdrDsArtItemEndpoint extends
		CoreAbstBsnsItemEndpoint<CdrDsArtItem> {

	@Inject
	private CdrDsArtItemEJB ejb;
	
	@Inject
	private CdrDsArtItemLookup lookup;

	@Override
	protected CoreAbstBsnsItemLookup<CdrDsArtItem> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstBsnsItemEJB<CdrDsArtItem> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return CdrDsArtItem_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<CdrDsArtItem> newSearchResult(Long size,
			List<CdrDsArtItem> resultList,
			CoreAbstBsnsItemSearchInput<CdrDsArtItem> searchInput) {
		return new CdrDsArtItemSearchResult(size, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsItemSearchInput<CdrDsArtItem> newSearchInput() {
		return new CdrDsArtItemSearchInput();
	}

}