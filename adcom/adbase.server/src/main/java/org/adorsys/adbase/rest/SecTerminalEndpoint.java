package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.SecTerminal;
import org.adorsys.adbase.jpa.SecTerminalSearchInput;
import org.adorsys.adbase.jpa.SecTerminalSearchResult;
import org.adorsys.adbase.jpa.SecTerminal_;
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
@Path("/secterminals")
public class SecTerminalEndpoint extends
		CoreAbstIdentifiedEndpoint<SecTerminal> {

	@Inject
	private SecTerminalEJB ejb;
	@Inject
	private SecTerminalLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<SecTerminal> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<SecTerminal> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return SecTerminal_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<SecTerminal> newSearchInput() {
		return new SecTerminalSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<SecTerminal> newSearchResult(
			Long count, Long total, List<SecTerminal> resultList,
			CoreAbstIdentifObjectSearchInput<SecTerminal> searchInput) {
		return new SecTerminalSearchResult(count, total, resultList,
				searchInput);
	}
}