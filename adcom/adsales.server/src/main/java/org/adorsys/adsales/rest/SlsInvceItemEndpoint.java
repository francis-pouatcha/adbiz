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
import org.adorsys.adsales.jpa.SlsInvceItem;
import org.adorsys.adsales.jpa.SlsInvceItem_;
import org.adorsys.adsales.jpa.SlsInvceItemSearchInput;
import org.adorsys.adsales.jpa.SlsInvceItemSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/slsinvceitems")
public class SlsInvceItemEndpoint extends
		CoreAbstBsnsItemEndpoint<SlsInvceItem> {

	@Inject
	private SlsInvceItemEJB ejb;
	@Inject
	private SlsInvceItemLookup lookup;

	@Override
	protected CoreAbstBsnsItemLookup<SlsInvceItem> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstBsnsItemEJB<SlsInvceItem> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return SlsInvceItem_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<SlsInvceItem> newSearchResult(Long size,
			List<SlsInvceItem> resultList,
			CoreAbstBsnsItemSearchInput<SlsInvceItem> searchInput) {
		return new SlsInvceItemSearchResult(size, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsItemSearchInput<SlsInvceItem> newSearchInput() {
		return new SlsInvceItemSearchInput();
	}

}