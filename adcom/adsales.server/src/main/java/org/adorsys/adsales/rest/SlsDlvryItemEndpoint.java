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
import org.adorsys.adsales.jpa.SlsDlvryItem;
import org.adorsys.adsales.jpa.SlsDlvryItem_;
import org.adorsys.adsales.jpa.SlsDlvryItemSearchInput;
import org.adorsys.adsales.jpa.SlsDlvryItemSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/slsinvceitems")
public class SlsDlvryItemEndpoint extends
		CoreAbstBsnsItemEndpoint<SlsDlvryItem> {

	@Inject
	private SlsDlvryItemEJB ejb;
	@Inject
	private SlsDlvryItemLookup lookup;

	@Override
	protected CoreAbstBsnsItemLookup<SlsDlvryItem> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstBsnsItemEJB<SlsDlvryItem> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return SlsDlvryItem_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<SlsDlvryItem> newSearchResult(Long size,
			List<SlsDlvryItem> resultList,
			CoreAbstBsnsItemSearchInput<SlsDlvryItem> searchInput) {
		return new SlsDlvryItemSearchResult(size, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsItemSearchInput<SlsDlvryItem> newSearchInput() {
		return new SlsDlvryItemSearchInput();
	}

}