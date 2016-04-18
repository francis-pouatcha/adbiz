package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.BaseCountryName;
import org.adorsys.adbase.jpa.BaseCountryNameSearchInput;
import org.adorsys.adbase.jpa.BaseCountryNameSearchResult;
import org.adorsys.adbase.jpa.BaseCountryName_;
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
@Path("/basecountrynames")
public class BaseCountryNameEndpoint extends CoreAbstIdentifiedEndpoint<BaseCountryName>{
	@Inject
	private BaseCountryNameEJB ejb;
	@Inject
	private BaseCountryNameLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<BaseCountryName> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<BaseCountryName> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return BaseCountryName_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<BaseCountryName> newSearchInput() {
		return new BaseCountryNameSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<BaseCountryName> newSearchResult(
			Long count, Long total, List<BaseCountryName> resultList,
			CoreAbstIdentifObjectSearchInput<BaseCountryName> searchInput) {
		return new BaseCountryNameSearchResult(count, total, resultList, searchInput);
	}
}