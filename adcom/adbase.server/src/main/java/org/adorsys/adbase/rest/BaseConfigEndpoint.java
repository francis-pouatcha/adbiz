package org.adorsys.adbase.rest;

import java.lang.reflect.Field;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adbase.jpa.BaseConfig;
import org.adorsys.adbase.jpa.BaseConfig_;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/baseconfigs")
public class BaseConfigEndpoint extends CoreAbstIdentifiedEndpoint<BaseConfig>{

	@Inject
	private BaseConfigEJB ejb;
	@Inject
	private BaseConfigLookup lookup;

	@Override
	protected CoreAbstIdentifiedEJB<BaseConfig> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return BaseConfig_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifiedLookup<BaseConfig> getLookup() {
		return lookup;
	}
}