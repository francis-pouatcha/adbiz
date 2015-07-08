package org.adorsys.adcatal.rest;

import java.lang.reflect.Field;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcatal.jpa.CatalArtManufSupp_;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalartmanufsupps")
public class CatalArtManufSuppEndpoint extends
		CoreAbstIdentifiedEndpoint<CatalArtManufSupp> {

	@Inject
	private CatalArtManufSuppEJB ejb;
	@Inject
	private CatalArtManufSuppLookup lookup;

	@Override
	protected CoreAbstIdentifiedLookup<CatalArtManufSupp> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalArtManufSupp> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return CatalArtManufSupp_.class.getFields();
	}
}