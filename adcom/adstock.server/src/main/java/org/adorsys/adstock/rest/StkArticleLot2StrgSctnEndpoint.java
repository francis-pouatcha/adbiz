package org.adorsys.adstock.rest;

import java.lang.reflect.Field;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
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
	protected CoreAbstIdentifiedLookup<StkArticleLot2StrgSctn> getLookup() {
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

}