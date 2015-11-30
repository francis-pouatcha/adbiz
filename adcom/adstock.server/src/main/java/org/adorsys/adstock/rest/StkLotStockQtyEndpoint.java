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
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.jpa.StkLotStockQtySearchInput;
import org.adorsys.adstock.jpa.StkLotStockQtySearchResult;
import org.adorsys.adstock.jpa.StkLotStockQty_;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/stklotstockqtys")
public class StkLotStockQtyEndpoint extends
	CoreAbstIdentifReadEndpoint<StkLotStockQty> {

	@Inject
	private StkLotStockQtyLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<StkLotStockQty> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return StkLotStockQty_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<StkLotStockQty> newSearchInput() {
		return new StkLotStockQtySearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<StkLotStockQty> newSearchResult(
			Long count, Long total, List<StkLotStockQty> resultList,
			CoreAbstIdentifObjectSearchInput<StkLotStockQty> searchInput) {
		return new StkLotStockQtySearchResult(count, resultList, searchInput);
	}
}