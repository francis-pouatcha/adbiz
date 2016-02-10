package org.adorsys.adprocmt.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectEndpoint;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adcore.xls.CoreAbstLoaderRegistration;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliverySearchInput;
import org.adorsys.adprocmt.jpa.PrcmtDeliverySearchResult;
import org.adorsys.adprocmt.jpa.PrcmtDelivery_;
import org.adorsys.adprocmt.loader.PrcmtLoaderRegistration;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/prcmtdeliverys")
public class PrcmtDeliveryEndpoint extends CoreAbstBsnsObjectEndpoint<PrcmtDelivery, CoreAbstBsnsObjectSearchInput<PrcmtDelivery>, CoreAbstBsnsObjectSearchResult<PrcmtDelivery>>
{

	@Inject
	private PrcmtDeliveryLookup lookup;
	
	@Inject
	private PrcmtLoaderRegistration loaderRegistration;
	
	@Override
	protected CoreAbstBsnsObjectLookup<PrcmtDelivery> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return PrcmtDelivery_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsObjectSearchResult<PrcmtDelivery> newSearchResult(
			Long size, List<PrcmtDelivery> resultList,
			CoreAbstBsnsObjectSearchInput<PrcmtDelivery> searchInput) {
		return new PrcmtDeliverySearchResult(size, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsObjectSearchInput<PrcmtDelivery> newSearchInput() {
		return new PrcmtDeliverySearchInput();
	}

	@Override
	protected CoreAbstLoaderRegistration getLoaderRegistration() {
		return loaderRegistration;
	}
}