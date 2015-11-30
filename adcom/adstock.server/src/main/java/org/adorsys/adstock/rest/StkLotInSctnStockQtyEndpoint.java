package org.adorsys.adstock.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifReadEndpoint;
import org.adorsys.adstock.jpa.StkLotInSctnStockQty;
import org.adorsys.adstock.jpa.StkLotInSctnStockQtySearchInput;
import org.adorsys.adstock.jpa.StkLotInSctnStockQtySearchResult;
import org.adorsys.adstock.jpa.StkLotInSctnStockQty_;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/stklotinsctnstockqtys")
public class StkLotInSctnStockQtyEndpoint extends
	CoreAbstIdentifReadEndpoint<StkLotInSctnStockQty> {

	@Inject
	private StkLotInSctnStockQtyLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<StkLotInSctnStockQty> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return StkLotInSctnStockQty_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<StkLotInSctnStockQty> newSearchInput() {
		return new StkLotInSctnStockQtySearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<StkLotInSctnStockQty> newSearchResult(
			Long count, Long total, List<StkLotInSctnStockQty> resultList,
			CoreAbstIdentifObjectSearchInput<StkLotInSctnStockQty> searchInput) {
		return new StkLotInSctnStockQtySearchResult(count, resultList, searchInput);
	}
}