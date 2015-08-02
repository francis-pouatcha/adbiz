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
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctnSearchInput;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctnSearchResult;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/stkarticlelot2strgsctns")
public class StkArticleLot2StrgSctnEndpoint extends
		CoreAbstIdentifiedEndpoint<StkArticleLot2StrgSctn> {

	@Inject
	private StkArticleLot2StrgSctnEJB ejb;
	@Inject
	private StkArticleLot2StrgSctnLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<StkArticleLot2StrgSctn> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<StkArticleLot2StrgSctn> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return StkArticleLot2StrgSctn_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<StkArticleLot2StrgSctn> newSearchInput() {
		return new StkArticleLot2StrgSctnSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<StkArticleLot2StrgSctn> newSearchResult(
			Long count, Long total, List<StkArticleLot2StrgSctn> resultList,
			CoreAbstIdentifObjectSearchInput<StkArticleLot2StrgSctn> searchInput) {
		return new StkArticleLot2StrgSctnSearchResult(count, total, resultList, searchInput);
	}

}