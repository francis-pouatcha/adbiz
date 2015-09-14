package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.OrgContact;
import org.adorsys.adbase.jpa.OrgContactSearchInput;
import org.adorsys.adbase.jpa.OrgContactSearchResult;
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
@Path("/orgcontacts")
public class OrgContactEndpoint extends CoreAbstIdentifiedEndpoint<OrgContact> {

	@Inject
	private OrgContactEJB ejb;
	@Inject
	private OrgContactLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<OrgContact> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<OrgContact> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return OrgContact.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<OrgContact> newSearchInput() {
		return new OrgContactSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<OrgContact> newSearchResult(
			Long count, Long total, List<OrgContact> resultList,
			CoreAbstIdentifObjectSearchInput<OrgContact> searchInput) {
		return new OrgContactSearchResult(count, total, resultList, searchInput);
	}
}