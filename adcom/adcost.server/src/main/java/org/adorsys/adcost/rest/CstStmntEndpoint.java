package org.adorsys.adcost.rest;

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
import org.adorsys.adcost.ejb.CstStmntLookup;
import org.adorsys.adcost.jpa.CstStmnt;
import org.adorsys.adcost.jpa.CstStmnt_;
import org.adorsys.adcost.jpa.CstStmntSearchInput;
import org.adorsys.adcost.jpa.CstStmntSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/invinvtrys")
public class CstStmntEndpoint extends CoreAbstBsnsObjectEndpoint<CstStmnt, CoreAbstBsnsObjectSearchInput<CstStmnt>, CoreAbstBsnsObjectSearchResult<CstStmnt>>
{

	@Inject
	private CstStmntLookup lookup;
	
	@Override
	protected CoreAbstBsnsObjectLookup<CstStmnt> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return CstStmnt_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsObjectSearchResult<CstStmnt> newSearchResult(
			Long size, List<CstStmnt> resultList,
			CoreAbstBsnsObjectSearchInput<CstStmnt> searchInput) {
		return new CstStmntSearchResult(size, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsObjectSearchInput<CstStmnt> newSearchInput() {
		return new CstStmntSearchInput();
	}
}