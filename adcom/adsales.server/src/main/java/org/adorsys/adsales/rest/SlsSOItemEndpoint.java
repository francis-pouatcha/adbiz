package org.adorsys.adsales.rest;

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
import org.adorsys.adsales.jpa.SlsSOItem;
import org.adorsys.adsales.jpa.SlsSOItem_;
import org.adorsys.adsales.jpa.SlsSOItemSearchInput;
import org.adorsys.adsales.jpa.SlsSOItemSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/slssoitems")
public class SlsSOItemEndpoint extends CoreAbstBsnsItemEndpoint<SlsSOItem> {

	@Inject
	private SlsSOItemEJB ejb;
	@Inject
	private SlsSOItemLookup lookup;

	@Override
	protected CoreAbstBsnsItemLookup<SlsSOItem> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstBsnsItemEJB<SlsSOItem> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return SlsSOItem_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<SlsSOItem> newSearchResult(Long size,
			List<SlsSOItem> resultList,
			CoreAbstBsnsItemSearchInput<SlsSOItem> searchInput) {
		return new SlsSOItemSearchResult(size, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsItemSearchInput<SlsSOItem> newSearchInput() {
		return new SlsSOItemSearchInput();
	}
}