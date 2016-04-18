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
import org.adorsys.adcshdwr.jpa.CdrPymnt;
import org.adorsys.adcshdwr.jpa.CdrPymntSearchInput;
import org.adorsys.adcshdwr.jpa.CdrPymntSearchResult;
import org.adorsys.adcshdwr.jpa.CdrPymnt_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/cdrpymnts")
public class CdrPymntEndpoint extends CoreAbstIdentifiedEndpoint<CdrPymnt>{

	@Inject
	private CdrPymntEJB ejb;
	@Inject
	private CdrPymntLookup lookup;
	
	@Override
	protected CoreAbstIdentifiedEJB<CdrPymnt> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstIdentifLookup<CdrPymnt> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return CdrPymnt_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<CdrPymnt> newSearchInput() {
		return new CdrPymntSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<CdrPymnt> newSearchResult(Long count, Long total,
			List<CdrPymnt> resultList, CoreAbstIdentifObjectSearchInput<CdrPymnt> searchInput) {
		return new CdrPymntSearchResult(count, total, resultList, searchInput);
	}

}