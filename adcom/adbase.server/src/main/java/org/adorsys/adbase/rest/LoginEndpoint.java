package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.jpa.LoginSearchInput;
import org.adorsys.adbase.jpa.LoginSearchResult;
import org.adorsys.adbase.jpa.Login_;
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
@Path("/logins")
public class LoginEndpoint extends CoreAbstIdentifiedEndpoint<Login> {
	@Inject
	private LoginEJB ejb;
	@Inject
	private LoginLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<Login> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<Login> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return Login_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<Login> newSearchInput() {
		return new LoginSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<Login> newSearchResult(
			Long count, Long total, List<Login> resultList,
			CoreAbstIdentifObjectSearchInput<Login> searchInput) {
		return new LoginSearchResult(count, total, resultList, searchInput);
	}
}