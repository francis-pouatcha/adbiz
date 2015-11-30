package org.adorsys.adprocmt.api;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;

import org.adorsys.adcore.exceptions.AdRestException;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryCstr;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.jpa.PrcmtDeliverySearchInput;
import org.adorsys.adprocmt.jpa.PrcmtDeliverySearchResult;
import org.adorsys.adprocmt.jpa.PrcmtDelivery_;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2PO;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2POItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctn;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.adorsys.adprocmt.rest.PrcmtDeliveryInjector;
import org.adorsys.adprocmt.rest.PrcmtDlvry2OuEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvry2POEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryItem2OuEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryItem2POItemEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryItem2StrgSctnEJB;

@Stateless
public class PrcmtDeliveryManager  extends CoreAbstBsnsObjectManager<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr, CoreAbstBsnsObjectSearchInput<PrcmtDelivery>>{
	@Inject
	private PrcmtDeliveryInjector injector;
	
	@Override
	protected CoreAbstBsnsObjInjector<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr> getInjector() {
		return injector;
	}

	@Override
	protected Field[] getEntityFields() {
		return PrcmtDelivery_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsObjectSearchInput<PrcmtDelivery> newSearchInput() {
		return new PrcmtDeliverySearchInput();
	}

	@Override
	protected CoreAbstBsnsObjectSearchResult<PrcmtDelivery> newSearchResult(
			Long size, List<PrcmtDelivery> resultList,
			CoreAbstBsnsObjectSearchInput<PrcmtDelivery> searchInput) {
		return new PrcmtDeliverySearchResult(size, resultList, searchInput);
	}

	@Override
	public PrcmtDelivery initiateBsnsObj(PrcmtDelivery bsnsObj) {
		bsnsObj = super.initiateBsnsObj(bsnsObj);
		return bsnsObj;
	}

	@Inject
	private PrcmtDlvry2OuEJB dlvry2OuEJB;
	public PrcmtDlvry2Ou addOu(String identif, PrcmtDlvry2Ou ou) throws AdRestException {
		PrcmtDlvry2Ou prcmtDlvry2Ou = new PrcmtDlvry2Ou();
		prcmtDlvry2Ou.setCntnrIdentif(identif);
		prcmtDlvry2Ou.setRcvngOrgUnit(ou.getRcvngOrgUnit());
		prcmtDlvry2Ou.setQtyPct(ou.getQtyPct());
		return dlvry2OuEJB.create(prcmtDlvry2Ou);
	}
	public PrcmtDlvry2Ou removeOu(String dlvryIdentif, String ouIdentif) throws AdRestException {
		String identif = PrcmtDlvry2Ou.toId(dlvryIdentif, ouIdentif);
		return dlvry2OuEJB.deleteByIdentif(identif);
	}

	@Inject
	private PrcmtDlvry2POEJB dlvry2POEJB;
	public PrcmtDlvry2PO addPo(String identif, PrcmtDlvry2PO po) throws AdRestException {
		PrcmtDlvry2PO prcmtDlvry2Po = new PrcmtDlvry2PO();
		prcmtDlvry2Po.setCntnrIdentif(identif);
		prcmtDlvry2Po.setPoNbr(po.getPoNbr());
		return dlvry2POEJB.create(prcmtDlvry2Po);
	}
	public PrcmtDlvry2PO removePo(String dlvryIdentif, String poIdentif) throws AdRestException {
		String identif = PrcmtDlvry2PO.toId(dlvryIdentif, poIdentif);
		return dlvry2POEJB.deleteByIdentif(identif);
	}

	@Inject
	private PrcmtDlvryItem2OuEJB item2OuEJB;
	public PrcmtDlvryItem2Ou addOu2Item(String itemIdentif, 
			PrcmtDlvryItem2Ou ou) throws AdRestException {
		PrcmtDlvryItem2Ou item2Ou = new PrcmtDlvryItem2Ou();
		item2Ou.copyFrom(ou);
		item2Ou.setCntnrIdentif(itemIdentif);
		return item2OuEJB.create(item2Ou);
	}

	@DELETE
	public PrcmtDlvryItem2Ou removeOuFromItem(String itemIdentif, String ouIdentif) throws AdRestException {
		String identif = PrcmtDlvry2PO.toId(itemIdentif, ouIdentif);
		return item2OuEJB.deleteByIdentif(identif);
	}
	
	/**
	 * The qty delivered is not set in this phase.
	 */
	@Inject
	private PrcmtDlvryItem2POItemEJB item2poItemEJB;
	public PrcmtDlvryItem2POItem addPoItem2Item(String itemIdentif, 
			PrcmtDlvryItem2POItem poItem) throws AdRestException {
		PrcmtDlvryItem2POItem item2PoItem = new PrcmtDlvryItem2POItem();
		item2PoItem.copyFrom(poItem);
		item2PoItem.setCntnrIdentif(itemIdentif);
		return item2poItemEJB.create(item2PoItem);
	}
	public PrcmtDlvryItem2POItem mapPoItem2Item(String itemIdentif, PrcmtPOItem poItem){
		PrcmtDlvryItem2POItem item2PoItem = new PrcmtDlvryItem2POItem();
		item2PoItem.setCntnrIdentif(itemIdentif);
		item2PoItem.setFreeQty(poItem.getFreeQty());
		item2PoItem.setPoItemNbr(poItem.getIdentif());
		item2PoItem.setQtyOrdered(poItem.getTrgtQty());
		return item2PoItem;
	}
	public PrcmtDlvryItem2POItem removePoItemFromItem(String itemIdentif, 
			String poItemIdentif) throws AdRestException {
		String identif = PrcmtDlvryItem2POItem.toId(itemIdentif, poItemIdentif);
		return item2poItemEJB.deleteByIdentif(identif);
	}
	
	@Inject
	private PrcmtDlvryItem2StrgSctnEJB prcmtDlvryItem2StrgSctnEJB;
	public PrcmtDlvryItem2StrgSctn addStrgSctn2Item(String itemIdentif, 
			PrcmtDlvryItem2StrgSctn section) throws AdRestException {
		PrcmtDlvryItem2StrgSctn item2Section = new PrcmtDlvryItem2StrgSctn();
		item2Section.copyFrom(section);
		item2Section.setCntnrIdentif(itemIdentif);
		item2Section.setStrgSection(section.getIdentif());
		return prcmtDlvryItem2StrgSctnEJB.create(item2Section);
	}
	public PrcmtDlvryItem2StrgSctn removeStrgSctnFromItem(String itemIdentif, 
			String sectionIdentif) throws AdRestException {
		String identif = PrcmtDlvryItem2StrgSctn.toId(itemIdentif, sectionIdentif);
		return prcmtDlvryItem2StrgSctnEJB.deleteByIdentif(identif);
	}
}
