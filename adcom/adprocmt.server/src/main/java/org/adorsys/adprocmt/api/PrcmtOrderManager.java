package org.adorsys.adprocmt.api;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.exceptions.AdRestException;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2PO;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2POItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctn;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderCstr;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderHstry;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderSearchInput;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderSearchResult;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder_;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.adorsys.adprocmt.rest.PrcmtProcOrderInjector;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class PrcmtOrderManager extends CoreAbstBsnsObjectManager<PrcmtProcOrder, PrcmtPOItem, PrcmtProcOrderHstry, PrcmtJob, PrcmtStep, PrcmtProcOrderCstr, CoreAbstBsnsObjectSearchInput<PrcmtProcOrder>>{

	@Inject
	private PrcmtProcOrderInjector injector;

	@Override
	protected CoreAbstBsnsObjInjector<PrcmtProcOrder, PrcmtPOItem, PrcmtProcOrderHstry, PrcmtJob, PrcmtStep, PrcmtProcOrderCstr> getInjector() {
		return injector;
	}

	@Override
	protected Field[] getEntityFields() {
		return PrcmtProcOrder_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsObjectSearchResult<PrcmtProcOrder> newSearchResult(Long size, List<PrcmtProcOrder> resultList,
			CoreAbstBsnsObjectSearchInput<PrcmtProcOrder> searchInput) {
		return new PrcmtProcOrderSearchResult(size, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsObjectSearchInput<PrcmtProcOrder> newSearchInput() {
		return new PrcmtProcOrderSearchInput();
	}

	@Inject
	private PrcmtDeliveryManager deliveryManager;
	public PrcmtDelivery order2Delivery(PrcmtProcOrder procOrder) throws AdRestException{
		
		if(!StringUtils.equals(CoreProcessStatusEnum.CLOSED.name(), procOrder.getStatus())) {
			 procOrder = close(procOrder.getIdentif());
		}
		PrcmtDelivery delivery = new PrcmtDelivery();
		delivery.fillDataFromOrder(procOrder);
		delivery = deliveryManager.initiateBsnsObj(delivery);
		
		PrcmtDlvry2PO dlvry2Po = new PrcmtDlvry2PO();
		dlvry2Po.setCntnrIdentif(delivery.getIdentif());
		dlvry2Po.setPoNbr(procOrder.getIdentif());
		deliveryManager.addPo(delivery.getIdentif(), dlvry2Po );
		if(StringUtils.isNotBlank(procOrder.getOuIdentif())){
			PrcmtDlvry2Ou dlvry2Ou = new PrcmtDlvry2Ou();
			dlvry2Ou.setCntnrIdentif(delivery.getIdentif());
			dlvry2Ou.setQtyPct(BigDecimalUtils.HUNDRED);
			dlvry2Ou.setRcvngOrgUnit(procOrder.getOuIdentif());
			deliveryManager.addOu(delivery.getIdentif(), dlvry2Ou);
		}
		
		// Count all order items.
		Long itemCount = injector.getItemLookup().countByCntnrIdentif(procOrder.getIdentif());
		int iter = 0;
		int max = 100;
		while(iter<itemCount){
			int start = iter;
			iter+=max;
			List<PrcmtPOItem> poItems = injector.getItemLookup().findByCntnrIdentif(procOrder.getIdentif(), start, max);
			for (PrcmtPOItem poItem : poItems) {
				PrcmtDlvryItem dlvryItem = new PrcmtDlvryItem();
				dlvryItem.fillDataFromOrderItem(poItem);
				dlvryItem.evlte();
				dlvryItem.setCntnrIdentif(delivery.getIdentif());
				dlvryItem = deliveryManager.addItem(delivery.getIdentif(), dlvryItem);
				PrcmtDlvryItem2POItem dlvryItem2POItem = deliveryManager.mapPoItem2Item(dlvryItem.getIdentif(), poItem);
				deliveryManager.addPoItem2Item(dlvryItem.getIdentif(), dlvryItem2POItem);

				if(StringUtils.isNotBlank(poItem.getOrgUnit())){
					PrcmtDlvryItem2Ou dlvryItem2Ou = new PrcmtDlvryItem2Ou();
					dlvryItem2Ou.setCntnrIdentif(dlvryItem.getIdentif());
					dlvryItem2Ou.setFreeQty(poItem.getFreeQty());
					dlvryItem2Ou.setQtyDlvrd(dlvryItem.getTrgtQty());
					dlvryItem2Ou.setRcvngOrgUnit(poItem.getOrgUnit());
					deliveryManager.addOu2Item(dlvryItem.getIdentif(), dlvryItem2Ou);
				}
				
				if(StringUtils.isNotBlank(poItem.getSection())){
					PrcmtDlvryItem2StrgSctn item2ection = new PrcmtDlvryItem2StrgSctn();
					item2ection.setCntnrIdentif(dlvryItem.getIdentif());
					deliveryManager.addStrgSctn2Item(dlvryItem.getIdentif(), item2ection);
				}
			}
		}
		return delivery;
	}
}
