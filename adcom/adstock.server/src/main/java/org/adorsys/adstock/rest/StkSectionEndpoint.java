package org.adorsys.adstock.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.jpa.StkSection_;
import org.adorsys.adstock.jpa.StkSectionSearchInput;
import org.adorsys.adstock.jpa.StkSectionSearchResult;
import org.apache.commons.lang3.StringUtils;

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
		return new StkSectionSearchResult();
	}
}