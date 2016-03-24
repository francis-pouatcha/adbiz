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
import org.adorsys.adstock.jpa.StkArtStockQty;
import org.adorsys.adstock.jpa.StkArtStockQtySearchInput;
import org.adorsys.adstock.jpa.StkArtStockQtySearchResult;
import org.adorsys.adstock.jpa.StkArtStockQty_;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/stkartstockqtys")
public class StkArtStockQtyEndpoint extends
	CoreAbstIdentifReadEndpoint<StkArtStockQty> {

	@Inject
	private StkArtStockQtyLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<StkArtStockQty> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return StkArtStockQty_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<StkArtStockQty> newSearchInput() {
		return new StkArtStockQtySearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<StkArtStockQty> newSearchResult(
			Long count, Long total, List<StkArtStockQty> resultList,
			CoreAbstIdentifObjectSearchInput<StkArtStockQty> searchInput) {
		return new StkArtStockQtySearchResult(count, resultList, searchInput);
	}
}