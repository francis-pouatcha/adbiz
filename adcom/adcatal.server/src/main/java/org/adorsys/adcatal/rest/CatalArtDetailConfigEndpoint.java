package org.adorsys.adcatal.rest;

import java.lang.reflect.Field;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.adorsys.adcatal.jpa.CatalArtDetailConfig_;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalartdetailconfigs")
public class CatalArtDetailConfigEndpoint extends
		CoreAbstIdentifiedEndpoint<CatalArtDetailConfig> {

	@Inject
	private CatalArtDetailConfigEJB ejb;
	@Inject
	private CatalArtDetailConfigLookup lookup;

	@Override
	protected CoreAbstIdentifiedLookup<CatalArtDetailConfig> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalArtDetailConfig> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return CatalArtDetailConfig_.class.getFields();
	}

}