package org.adorsys.adprocmt.rest;

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
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderSearchInput;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/prcmtprocorders")
public class PrcmtProcOrderEndpoint extends CoreAbstBsnsObjectEndpoint<PrcmtProcOrder, CoreAbstBsnsObjectSearchInput<PrcmtProcOrder>, CoreAbstBsnsObjectSearchResult<PrcmtProcOrder>>
{

	@Inject
	private PrcmtProcOrderLookup lookup;

	@Override
	protected CoreAbstBsnsObjectLookup<PrcmtProcOrder> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return PrcmtProcOrder_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsObjectSearchResult<PrcmtProcOrder> newSearchResult(Long size, List<PrcmtProcOrder> resultList,
			CoreAbstBsnsObjectSearchInput<PrcmtProcOrder> searchInput) {
		return new PrcmtProcOrderSearchResult(size, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsObjectSearchInput<PrcmtProcOrder> newSearchInput() {
		return new PrcmtProcOrderSearchInput();
	}

}