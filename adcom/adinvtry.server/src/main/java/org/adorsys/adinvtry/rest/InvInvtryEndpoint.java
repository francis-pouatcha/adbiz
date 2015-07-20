package org.adorsys.adinvtry.rest;

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
import org.adorsys.adinvtry.jpa.InvInvtry;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/invinvtrys")
public class InvInvtryEndpoint extends CoreAbstBsnsObjectEndpoint<InvInvtry, CoreAbstBsnsObjectSearchInput<InvInvtry>, CoreAbstBsnsObjectSearchResult<InvInvtry>>
{

	@Inject
	private InvInvtryLookup lookup;
	
	@Override
	protected CoreAbstBsnsObjectLookup<InvInvtry> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return InvInvtry_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsObjectSearchResult<InvInvtry> newSearchResult(
			Long size, List<InvInvtry> resultList,
			CoreAbstBsnsObjectSearchInput<InvInvtry> searchInput) {
		return new InvInvtrySearchResult(size, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsObjectSearchInput<InvInvtry> newSearchInput() {
		return new InvInvtrySearchInput();
	}
}