package org.adorsys.adprocmt.api;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.adorsys.adcore.exceptions.AdRestException;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.rest.CoreAbstBsnsManagerEndpoint;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.jpa.PrcmtPOItemSearchInput;
import org.adorsys.adprocmt.jpa.PrcmtPOItemSearchResult;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderCstr;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderHstry;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.adorsys.adprocmt.rest.PrcmtProcOrderInjector;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/order")
public class PrcmtOrderManagerEndpoint extends
		CoreAbstBsnsManagerEndpoint<PrcmtProcOrder, PrcmtPOItem, PrcmtProcOrderHstry, PrcmtJob, PrcmtStep, PrcmtProcOrderCstr> {

	@Inject
	private PrcmtOrderManager manager;
	@Inject
	private PrcmtProcOrderInjector injector;

	@Override
	protected CoreAbstBsnsObjectManager<PrcmtProcOrder, PrcmtPOItem, PrcmtProcOrderHstry, PrcmtJob, PrcmtStep, PrcmtProcOrderCstr, CoreAbstBsnsObjectSearchInput<PrcmtProcOrder>> getBsnsObjManager() {
		return manager;
	}

	@Override
	protected CoreAbstBsnsObjInjector<PrcmtProcOrder, PrcmtPOItem, PrcmtProcOrderHstry, PrcmtJob, PrcmtStep, PrcmtProcOrderCstr> getInjector() {
		return injector;
	}

	@Override
	protected CoreAbstBsnsItemSearchInput<PrcmtPOItem> newItemSearchInput() {
		return new PrcmtPOItemSearchInput();
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<PrcmtPOItem> newItemSearchResult(long count, List<PrcmtPOItem> resultList,
			CoreAbstBsnsItemSearchInput<PrcmtPOItem> itemSearchInput) {
		return new PrcmtPOItemSearchResult(count, resultList, itemSearchInput);
	}

	@POST
	@Path("/transform")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public PrcmtDelivery order2Delivery(PrcmtProcOrder prcmtOrder) throws AdRestException {
		return manager.order2Delivery(prcmtOrder);
	}
	
	@PUT
	@Path("/prepare/{identif}")
	public Response prepareObj(@PathParam("identif") String identif) {
		manager.prepareProcmtOrder(identif);
		return Response.ok().build();
	}

}