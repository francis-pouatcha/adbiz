package org.adorsys.adprocmt.rest;

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
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2POItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2POItemSearchInput;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2POItemSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/prcmtdlvryitem2poitems")
public class PrcmtDlvryItem2POItemEndpoint extends CoreAbstIdentifReadEndpoint<PrcmtDlvryItem2POItem>
{

	@Inject
	private PrcmtDlvryItem2POItemLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<PrcmtDlvryItem2POItem> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return PrcmtDlvryItem2POItem_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<PrcmtDlvryItem2POItem> newSearchInput() {
		return new PrcmtDlvryItem2POItemSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<PrcmtDlvryItem2POItem> newSearchResult(Long count, Long total,
			List<PrcmtDlvryItem2POItem> resultList, CoreAbstIdentifObjectSearchInput<PrcmtDlvryItem2POItem> searchInput) {
		return new PrcmtDlvryItem2POItemSearchResult(count, resultList, searchInput);
	}
}