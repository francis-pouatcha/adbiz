package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.SecTermRegist;
import org.adorsys.adbase.jpa.SecTermRegistSearchInput;
import org.adorsys.adbase.jpa.SecTermRegistSearchResult;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/secterminalregist")
public class SecTermRegistEndpoint extends
		CoreAbstIdentifiedEndpoint<SecTermRegist> {

	@Inject
	private SecTermRegistEJB ejb;
	@Inject
	private SecTermRegistLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<SecTermRegist> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<SecTermRegist> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return SecTermRegist.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<SecTermRegist> newSearchInput() {
		return new SecTermRegistSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<SecTermRegist> newSearchResult(
			Long count, Long total, List<SecTermRegist> resultList,
			CoreAbstIdentifObjectSearchInput<SecTermRegist> searchInput) {
		return new SecTermRegistSearchResult(count, total, resultList, searchInput);
	}
}