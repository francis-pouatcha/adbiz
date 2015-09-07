package org.adorsys.adstock.rest;

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
import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.jpa.StkSection_;
import org.adorsys.adstock.jpa.StkSectionSearchInput;
import org.adorsys.adstock.jpa.StkSectionSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/stksections")
public class StkSectionEndpoint extends CoreAbstIdentifiedEndpoint<StkSection> {

	@Inject
	private StkSectionLookup lookup;
	@Inject
	private StkSectionEJB ejb;

	@Override
	protected CoreAbstIdentifLookup<StkSection> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<StkSection> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return StkSection_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<StkSection> newSearchInput() {
		return new StkSectionSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<StkSection> newSearchResult(
			Long count, Long total, List<StkSection> resultList,
			CoreAbstIdentifObjectSearchInput<StkSection> searchInput) {
		return new StkSectionSearchResult(count, total, resultList, searchInput);
	}
}