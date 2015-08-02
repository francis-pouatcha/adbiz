package org.adorsys.adprocmt.trigger;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adprocmt.api.PrcmtOrderHolder;
import org.adorsys.adprocmt.api.PrcmtOrderItemHolder;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.spi.dflt.ProcmtPOTriggerModeEnum;
import org.adorsys.adstock.jpa.StkAbstractArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
import org.adorsys.adstock.rest.StkLotStockQtyLookup;
/**
 * 
 * @author guymoyo
 *
 */
@Singleton
public class StockShortageTriggerModeHandler implements TriggerModeExecuter {

	public static final String key = ProcmtPOTriggerModeEnum.STOCK_SHORTAGE.name();
	
	public static String getKey() {
		return key;
	}

	@Inject
	private StkLotStockQtyLookup stkLotStockQtyLookup;
	@Inject
	private StkArticleLot2StrgSctnLookup stkArticleLot2StrgSctnLookup;
	
	@Override
	public PrcmtOrderHolder executeTriggerMode(PrcmtProcOrder prcmtProcOrder) {	
		if(prcmtProcOrder==null) return null;
		PrcmtOrderHolder prcmtProcOrderHolder = new PrcmtOrderHolder();
		prcmtProcOrderHolder.setPrcmtProcOrder(prcmtProcOrder);
		
		List<StkLotStockQty> itemsShortage = stkLotStockQtyLookup.itemsShortage(new BigDecimal(40));
		for(StkLotStockQty itemStock:itemsShortage){
			PrcmtPOItem prcmtPOItem = new PrcmtPOItem();
			PrcmtOrderItemHolder prcmtOrderItemHolder = new PrcmtOrderItemHolder();		
			List<StkArticleLot2StrgSctn> findByArtPic = stkArticleLot2StrgSctnLookup.findByArtPic(itemStock.getArtPic());
					
			if(!findByArtPic.isEmpty()){
				prcmtPOItem.setArtPic(itemStock.getArtPic());
//				BigDecimal stkQtyPreOrder = stkLotStockQtyLookup.countStockByArtPic(itemStock.getArtPic());
//				prcmtPOItem.setStkQtyPreOrder(stkQtyPreOrder);
				StkArticleLot2StrgSctn strgSctn = findByArtPic.get(0);
				StkAbstractArticleLot sectionArticleLot = strgSctn.getSectionArticleLot();
				prcmtPOItem.setArtName(strgSctn.getArtName());
				prcmtPOItem.setSection(itemStock.getSection());
				prcmtPOItem.setPrchUnitPrcCur(sectionArticleLot.getPrchUnitPrcCur());
				prcmtPOItem.setPrchUnitPrcPreTax(sectionArticleLot.getPrchUnitPrcPreTax());
				prcmtPOItem.setPrchVatPct(sectionArticleLot.getPrchVatPct());
				prcmtPOItem.setQtyDlvrd(BigDecimal.ONE);		
				prcmtOrderItemHolder.setPrcmtPOItem(prcmtPOItem);
				prcmtProcOrderHolder.getPoItems().add(prcmtOrderItemHolder);
			}			
		}	
		return prcmtProcOrderHolder;
	}

}
