package org.adorsys.adcatal.rest;

import java.lang.reflect.Field;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcatal.jpa.CatalArt2ProductFamily;
import org.adorsys.adcatal.jpa.CatalArt2ProductFamily_;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalart2productfamilys")
public class CatalArt2ProductFamilyEndPoint extends
		CoreAbstIdentifiedEndpoint<CatalArt2ProductFamily> {

	@Inject
	private CatalArt2ProductFamilyEJB ejb;
	@Inject
	private CatalArt2ProductFamilyLookup lookup;

	@Override
	protected CoreAbstIdentifiedLookup<CatalArt2ProductFamily> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalArt2ProductFamily> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return CatalArt2ProductFamily_.class.getFields();
	}
}