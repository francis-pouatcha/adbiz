package org.adorsys.adcatal.rest;

import java.lang.reflect.Field;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcatal.jpa.CatalProductFamily;
import org.adorsys.adcatal.jpa.CatalProductFamily_;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalproductfamilys")
public class CatalProductFamilyEndpoint extends
		CoreAbstIdentifiedEndpoint<CatalProductFamily> {

	@Inject
	private CatalProductFamilyEJB ejb;
	@Inject
	private CatalProductFamilyLookup lookup;

	@Override
	protected CoreAbstIdentifiedLookup<CatalProductFamily> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalProductFamily> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return CatalProductFamily_.class.getFields();
	}
}