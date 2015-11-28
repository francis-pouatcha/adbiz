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
import org.adorsys.adprocmt.jpa.PrcmtDlvry2PO;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2POSearchInput;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2POSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/prcmtdlvry2pos")
public class PrcmtDlvry2POEndpoint extends CoreAbstIdentifReadEndpoint<PrcmtDlvry2PO>
{

	@Inject
	private PrcmtDlvry2POLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<PrcmtDlvry2PO> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return PrcmtDlvry2PO_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<PrcmtDlvry2PO> newSearchInput() {
		return new PrcmtDlvry2POSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<PrcmtDlvry2PO> newSearchResult(Long count, Long total,
			List<PrcmtDlvry2PO> resultList, CoreAbstIdentifObjectSearchInput<PrcmtDlvry2PO> searchInput) {
		return new PrcmtDlvry2POSearchResult(count, resultList, searchInput);
	}
}