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
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifReadEndpoint;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliverySearchInput;
import org.adorsys.adprocmt.jpa.PrcmtDeliverySearchResult;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2OuSearchInput;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2OuSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/prcmtdlvry2ous")
public class PrcmtDlvry2OuEndpoint extends CoreAbstIdentifReadEndpoint<PrcmtDlvry2Ou>
{

	@Inject
	private PrcmtDlvry2OuLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<PrcmtDlvry2Ou> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return PrcmtDlvry2Ou_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<PrcmtDlvry2Ou> newSearchInput() {
		return new PrcmtDlvry2OuSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<PrcmtDlvry2Ou> newSearchResult(Long count, Long total,
			List<PrcmtDlvry2Ou> resultList, CoreAbstIdentifObjectSearchInput<PrcmtDlvry2Ou> searchInput) {
		return new PrcmtDlvry2OuSearchResult(count, resultList, searchInput);
	}
}