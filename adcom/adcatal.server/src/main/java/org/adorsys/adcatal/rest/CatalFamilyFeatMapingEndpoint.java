package org.adorsys.adcatal.rest;

import java.lang.reflect.Field;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcatal.jpa.CatalFamilyFeatMaping;
import org.adorsys.adcatal.jpa.CatalFamilyFeatMaping_;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalfamilyfeatmapings")
public class CatalFamilyFeatMapingEndpoint extends
		CoreAbstIdentifiedEndpoint<CatalFamilyFeatMaping> {

	@Inject
	private CatalFamilyFeatMapingEJB ejb;
	@Inject
	private CatalFamilyFeatMapingLookup lookup;

	@Override
	protected CoreAbstIdentifiedLookup<CatalFamilyFeatMaping> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalFamilyFeatMaping> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return CatalFamilyFeatMaping_.class.getFields();
	}

}