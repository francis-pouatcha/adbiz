package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.ConnectionHistory;
import org.adorsys.adbase.jpa.ConnectionHistorySearchInput;
import org.adorsys.adbase.jpa.ConnectionHistorySearchResult;
import org.adorsys.adbase.jpa.ConnectionHistory_;
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
@Path("/connectionhistorys")
public class ConnectionHistoryEndpoint extends
		CoreAbstIdentifiedEndpoint<ConnectionHistory> {

	@Inject
	private ConnectionHistoryEJB ejb;
	@Inject
	private ConnectionHistoryLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<ConnectionHistory> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<ConnectionHistory> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return ConnectionHistory_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<ConnectionHistory> newSearchInput() {
		return new ConnectionHistorySearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<ConnectionHistory> newSearchResult(
			Long count, Long total, List<ConnectionHistory> resultList,
			CoreAbstIdentifObjectSearchInput<ConnectionHistory> searchInput) {
		return new ConnectionHistorySearchResult(count, total, resultList,
				searchInput);
	}

}