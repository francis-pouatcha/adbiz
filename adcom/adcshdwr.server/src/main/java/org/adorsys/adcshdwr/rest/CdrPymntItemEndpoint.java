package org.adorsys.adcshdwr.rest;

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
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcshdwr.jpa.CdrPymntItem;
import org.adorsys.adcshdwr.jpa.CdrPymntItemSearchInput;
import org.adorsys.adcshdwr.jpa.CdrPymntItemSearchResult;
import org.adorsys.adcshdwr.jpa.CdrPymntItem_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/cdrpymntitems")
public class CdrPymntItemEndpoint extends CoreAbstIdentifiedEndpoint<CdrPymntItem> {

	@Inject
	private CdrPymntItemEJB ejb;
	@Inject
	private CdrPymntItemLookup lookup;

	@Override
	protected CoreAbstIdentifiedEJB<CdrPymntItem> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstIdentifLookup<CdrPymntItem> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return CdrPymntItem_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<CdrPymntItem> newSearchInput() {
		return new CdrPymntItemSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<CdrPymntItem> newSearchResult(Long count, Long total,
			List<CdrPymntItem> resultList, CoreAbstIdentifObjectSearchInput<CdrPymntItem> searchInput) {
		return new CdrPymntItemSearchResult(count, total, resultList, searchInput);
	}
}