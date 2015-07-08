package org.adorsys.adcatal.rest;

import java.lang.reflect.Field;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.jpa.CatalArtLangMapping_;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalartfeatmappings")
public class CatalArtLangMappingEndpoint extends
		CoreAbstIdentifiedEndpoint<CatalArtLangMapping> {

	@Inject
	private CatalArtLangMappingEJB ejb;
	@Inject
	private CatalArtLangMappingLookup lookup;
	
	@Override
	protected CoreAbstIdentifiedLookup<CatalArtLangMapping> getLookup() {
		return lookup;
	}
	@Override
	protected CoreAbstIdentifiedEJB<CatalArtLangMapping> getEjb() {
		return ejb;
	}
	@Override
	protected Field[] getEntityFields() {
		return CatalArtLangMapping_.class.getFields();
	}
}