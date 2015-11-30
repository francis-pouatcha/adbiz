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
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2OuSearchInput;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2OuSearchResult;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2Ou_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/prcmtdlvryitem2ous")
public class PrcmtDlvryItem2OuEndpoint extends CoreAbstIdentifReadEndpoint<PrcmtDlvryItem2Ou>
{

	@Inject
	private PrcmtDlvryItem2OuLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<PrcmtDlvryItem2Ou> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return PrcmtDlvryItem2Ou_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<PrcmtDlvryItem2Ou> newSearchInput() {
		return new PrcmtDlvryItem2OuSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<PrcmtDlvryItem2Ou> newSearchResult(Long count, Long total,
			List<PrcmtDlvryItem2Ou> resultList, CoreAbstIdentifObjectSearchInput<PrcmtDlvryItem2Ou> searchInput) {
		return new PrcmtDlvryItem2OuSearchResult(count, resultList, searchInput);
	}
}