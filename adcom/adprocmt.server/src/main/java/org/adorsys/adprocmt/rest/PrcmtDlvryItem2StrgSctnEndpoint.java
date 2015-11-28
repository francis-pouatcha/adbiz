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
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctn;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctnSearchInput;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctnSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/prcmtdlvryitem2strgsctns")
public class PrcmtDlvryItem2StrgSctnEndpoint extends CoreAbstIdentifReadEndpoint<PrcmtDlvryItem2StrgSctn>
{

	@Inject
	private PrcmtDlvryItem2StrgSctnLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<PrcmtDlvryItem2StrgSctn> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return PrcmtDlvryItem2StrgSctn_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<PrcmtDlvryItem2StrgSctn> newSearchInput() {
		return new PrcmtDlvryItem2StrgSctnSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<PrcmtDlvryItem2StrgSctn> newSearchResult(Long count, Long total,
			List<PrcmtDlvryItem2StrgSctn> resultList, CoreAbstIdentifObjectSearchInput<PrcmtDlvryItem2StrgSctn> searchInput) {
		return new PrcmtDlvryItem2StrgSctnSearchResult(count, resultList, searchInput);
	}
}