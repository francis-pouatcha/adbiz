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
import org.adorsys.adcore.props.AbstEntiyProps;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEndpoint;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItem;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItemSearchInput;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItemSearchResult;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItem_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/cdrdsartitems")
public class CdrDrctSalesItemEndpoint extends
		CoreAbstBsnsItemEndpoint<CdrDrctSalesItem, CoreAbstBsnsItemSearchInput<CdrDrctSalesItem>, CoreAbstBsnsItemSearchResult<CdrDrctSalesItem>> {

	@Inject
	private CdrDrctSalesItemLookup lookup;
	@Inject
	private CdrDrctSalesItemProps entityProps;

	@Override
	protected Field[] getEntityFields() {
		return CdrDrctSalesItem_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<CdrDrctSalesItem> newSearchResult(Long size,
			List<CdrDrctSalesItem> resultList,
			CoreAbstBsnsItemSearchInput<CdrDrctSalesItem> searchInput) {
		return new CdrDrctSalesItemSearchResult(size, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsItemSearchInput<CdrDrctSalesItem> newSearchInput() {
		return new CdrDrctSalesItemSearchInput();
	}

	@Override
	protected CoreAbstBsnsItemLookup<CdrDrctSalesItem> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<CdrDrctSalesItem> newSearchResult(Long count, Long total,
			List<CdrDrctSalesItem> resultList, CoreAbstBsnsItemSearchInput<CdrDrctSalesItem> searchInput) {
		return new CdrDrctSalesItemSearchResult(count, total, resultList, searchInput);
	}

	@Override
	protected AbstEntiyProps getEntityProps() {
		return entityProps;
	}

}