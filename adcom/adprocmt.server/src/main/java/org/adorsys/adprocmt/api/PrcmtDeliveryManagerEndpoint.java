package org.adorsys.adprocmt.api;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.adorsys.adcore.exceptions.AdRestException;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.rest.CoreAbstBsnsManagerEndpoint;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryCstr;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2PO;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2POItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctn;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemSearchInput;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemSearchResult;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.adorsys.adprocmt.rest.PrcmtDeliveryInjector;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/delivery")
public class PrcmtDeliveryManagerEndpoint extends CoreAbstBsnsManagerEndpoint<PrcmtDelivery, PrcmtDlvryItem, 
	PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr> {

	@Inject
	private PrcmtDeliveryManager manager;
	@Inject
	private PrcmtDeliveryInjector injector;
	
	@Override
	protected CoreAbstBsnsObjectManager<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr, CoreAbstBsnsObjectSearchInput<PrcmtDelivery>> getBsnsObjManager() {
		return manager;
	}

	@Override
	protected CoreAbstBsnsObjInjector<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr> getInjector() {
		return injector;
	}

	@Override
	protected CoreAbstBsnsItemSearchInput<PrcmtDlvryItem> newItemSearchInput() {
		return new PrcmtDlvryItemSearchInput();
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<PrcmtDlvryItem> newItemSearchResult(
			long count, List<PrcmtDlvryItem> resultList,
			CoreAbstBsnsItemSearchInput<PrcmtDlvryItem> itemSearchInput) {
		return new PrcmtDlvryItemSearchResult(count, resultList, itemSearchInput);
	}

	@POST
	@Path("/{identif}/ous")
	@Produces({ "application/json"})
	public PrcmtDlvry2Ou addOu(@PathParam("identif") String identif, PrcmtDlvry2Ou ou) throws AdRestException {
		return manager.addOu(identif, ou);
	}

	@DELETE
	@Path("/{dlvryIdentif}/ous/{ouIdentif}")
	@Produces({ "application/json"})
	public PrcmtDlvry2Ou removeOu(@PathParam("dlvryIdentif") String dlvryIdentif, @PathParam("ouIdentif") String ouIdentif) throws AdRestException {
		return manager.removeOu(dlvryIdentif, ouIdentif);
	}

	@POST
	@Path("/{identif}/pos")
	@Produces({ "application/json"})
	public PrcmtDlvry2PO addPo(@PathParam("identif") String identif, PrcmtDlvry2PO po) throws AdRestException {
		return manager.addPo(identif, po);
	}

	@DELETE
	@Path("/{dlvryIdentif}/pos/{poIdentif}/")
	@Produces({ "application/json"})
	public PrcmtDlvry2PO removePo(@PathParam("dlvryIdentif") String dlvryIdentif, @PathParam("poIdentif") String poIdentif) throws AdRestException {
		return manager.removePo(dlvryIdentif, poIdentif);
	}

	@POST
	@Path("/items/{itemIdentif}/ous")
	@Produces({ "application/json"})
	public PrcmtDlvryItem2Ou addOu2Item( @PathParam("itemIdentif") String itemIdentif, 
			PrcmtDlvryItem2Ou ou) throws AdRestException {
		return manager.addOu2Item(itemIdentif, ou);
	}

	@DELETE
	@Path("/items/{itemIdentif}/ous/{ouIdentif}")
	@Produces({ "application/json"})
	public PrcmtDlvryItem2Ou removeOuFromItem(@PathParam("itemIdentif") String itemIdentif, @PathParam("ouIdentif") String ouIdentif) throws AdRestException {
		return manager.removeOuFromItem(itemIdentif, ouIdentif);
	}

	@POST
	@Path("/items/{itemIdentif}/poItems")
	@Produces({ "application/json"})
	public PrcmtDlvryItem2POItem addPoItem2Item( @PathParam("itemIdentif") String itemIdentif, 
			PrcmtDlvryItem2POItem poItem) throws AdRestException {
		return manager.addPoItem2Item(itemIdentif, poItem);
	}

	@DELETE
	@Path("/items/{itemIdentif}/poItems/{poItemIdentif}")
	@Produces({ "application/json"})
	public PrcmtDlvryItem2POItem removePoItemFromItem(@PathParam("itemIdentif") String itemIdentif, 
			@PathParam("poItemIdentif") String poItemIdentif) throws AdRestException {
		return manager.removePoItemFromItem(itemIdentif, poItemIdentif);
	}
	
	@POST
	@Path("/items/{itemIdentif}/sections")
	@Produces({ "application/json"})
	public PrcmtDlvryItem2StrgSctn addStrgSctn2Item( @PathParam("itemIdentif") String itemIdentif, 
			PrcmtDlvryItem2StrgSctn section) throws AdRestException {
		return manager.addStrgSctn2Item(itemIdentif, section);
	}

	@DELETE
	@Path("/items/{itemIdentif}/sections/{sectionIdentif}")
	@Produces({ "application/json"})
	public PrcmtDlvryItem2StrgSctn removeStrgSctnFromItem(@PathParam("itemIdentif") String itemIdentif, 
			@PathParam("sectionIdentif") String sectionIdentif) throws AdRestException {
		return manager.removeStrgSctnFromItem(itemIdentif, sectionIdentif);
	}
}