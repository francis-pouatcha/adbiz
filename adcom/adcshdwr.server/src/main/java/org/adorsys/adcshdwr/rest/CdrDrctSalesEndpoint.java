package org.adorsys.adcshdwr.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectEndpoint;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesSearchInput;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/cdrdrctsaless")
public class CdrDrctSalesEndpoint extends CoreAbstBsnsObjectEndpoint<CdrDrctSales>{

	@Inject
	private CdrDrctSalesLookup lookup;

	@Override
	protected CoreAbstBsnsObjectLookup<CdrDrctSales> getLookup() {
		return lookup;
	}


	@Override
	protected Field[] getEntityFields() {
		return CdrDrctSales_.class.getFields();;
	}


	@Override
	protected CoreAbstBsnsObjectSearchResult<CdrDrctSales> newSearchResult(
			Long size, List<CdrDrctSales> resultList,
			CoreAbstBsnsObjectSearchInput<CdrDrctSales> searchInput) {
		return new CdrDrctSalesSearchResult(size, resultList, searchInput);
	}


	@Override
	protected CoreAbstBsnsObjectSearchInput<CdrDrctSales> newSearchInput() {
		return new CdrDrctSalesSearchInput();
	}


}