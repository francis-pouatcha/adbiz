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
import org.adorsys.adprocmt.jpa.PrcmtDlvryArtPrcssng;
import org.adorsys.adprocmt.jpa.PrcmtDlvryArtPrcssngSearchInput;
import org.adorsys.adprocmt.jpa.PrcmtDlvryArtPrcssngSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/prcmtdlvryartprcssngs")
public class PrcmtDlvryArtPrcssngEndpoint extends CoreAbstIdentifReadEndpoint<PrcmtDlvryArtPrcssng>
{

	@Inject
	private PrcmtDlvryArtPrcssngLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<PrcmtDlvryArtPrcssng> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return PrcmtDlvryArtPrcssng_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<PrcmtDlvryArtPrcssng> newSearchInput() {
		return new PrcmtDlvryArtPrcssngSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<PrcmtDlvryArtPrcssng> newSearchResult(Long count, Long total,
			List<PrcmtDlvryArtPrcssng> resultList, CoreAbstIdentifObjectSearchInput<PrcmtDlvryArtPrcssng> searchInput) {
		return new PrcmtDlvryArtPrcssngSearchResult(count, resultList, searchInput);
	}
}