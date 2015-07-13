package org.adorsys.adstock.rest;

import java.lang.reflect.Field;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.jpa.StkLotStockQty_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/stklotstockqtys")
public class StkLotStockQtyEndpoint extends
		CoreAbstIdentifiedEndpoint<StkLotStockQty> {

	@Inject
	private StkLotStockQtyEJB ejb;
	@Inject
	private StkLotStockQtyLookup lookup;

	@Override
	protected CoreAbstIdentifiedLookup<StkLotStockQty> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<StkLotStockQty> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return StkLotStockQty_.class.getFields();
	}
}