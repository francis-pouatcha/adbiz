package org.adorsys.adsales.rest.loader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adcore.auth.AdSystem;
import org.adorsys.adcore.enums.CoreRoleInProcessEnum;
import org.adorsys.adsales.api.SaleOrderManager;
import org.adorsys.adsales.api.SlsSOItemHolder;
import org.adorsys.adsales.api.SlsSOPtnrHolder;
import org.adorsys.adsales.api.SlsSalesOrderHolder;
import org.adorsys.adsales.jpa.SlsSOItem;
import org.adorsys.adsales.jpa.SlsSOPtnr;
import org.adorsys.adsales.jpa.SlsSalesOrder;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
import org.adorsys.adstock.rest.StkLotStockQtyLookup;
import org.apache.commons.lang3.StringUtils;

@Singleton
public class SlsSalesOrderManagerClient {

	@Inject
	private SaleOrderManager saleOrderManager;
	
	@Inject
	private StkArticleLot2StrgSctnLookup articleLot2StrgSctnLookup;
	
	@Inject
	private StkLotStockQtyLookup lotStockQtyLookup;
	
	private SlsSalesOrderHolder slsSalesOrderHolder = new SlsSalesOrderHolder();
	
	public void saveSalesOrder(SlsSalesOrderExcel salesOrderExcel){
		SlsSalesOrder slsSalesOrder = new SlsSalesOrder();
		salesOrderExcel.copyTo(slsSalesOrder);
		slsSalesOrderHolder.setSlsSalesOrder(slsSalesOrder);
		
		String ctmrNbr = salesOrderExcel.getCtmrNbr();
		SlsSOPtnrHolder ctmrHolder = new SlsSOPtnrHolder();
		SlsSOPtnr slsSoPtnr = new SlsSOPtnr();
		slsSoPtnr.setPtnrNbr(ctmrNbr);
		slsSoPtnr.setRoleInSO(CoreRoleInProcessEnum.CUSTOMER.name());
		ctmrHolder.setSlsSOPtnr(slsSoPtnr);
		slsSalesOrderHolder.getSlsSOPtnrsHolder().add(ctmrHolder);
		String insNbr = salesOrderExcel.getInsNbr();
		if(StringUtils.isNotBlank(insNbr)){
			SlsSOPtnrHolder insHolder = new SlsSOPtnrHolder();
			SlsSOPtnr insPtnr = new SlsSOPtnr();
			insPtnr.setPtnrNbr(insNbr);
			insPtnr.setRoleInSO(CoreRoleInProcessEnum.INSURER.name());
			insHolder.setSlsSOPtnr(insPtnr);
			slsSalesOrderHolder.getSlsSOPtnrsHolder().add(insHolder);
		}
	}
	
	public void saveSalesOrderItem(SlsSOItemExcel soItemExcel){
		SlsSOItem slsSOItem = new SlsSOItem();
		soItemExcel.copyTo(slsSOItem);
		SlsSOItemHolder slsSOItemHolder = new SlsSOItemHolder();
		slsSOItemHolder.setSlsSOItem(slsSOItem);
		slsSalesOrderHolder.getSlsSOItemsholder().add(slsSOItemHolder);
	}

	private List<SlsSalesOrderHolder> holders = new ArrayList<SlsSalesOrderHolder>();
	public void done() {
		holders.add(slsSalesOrderHolder);
		slsSalesOrderHolder = new SlsSalesOrderHolder();
	}
	
	@AdSystem
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void doSale(){
		if(holders.isEmpty()) return;
		SlsSalesOrderHolder holder = holders.remove(0);
		List<SlsSOItemHolder> slsSOItemsholder = holder.getSlsSOItemsholder();
		for (SlsSOItemHolder slsSOItemHolder : slsSOItemsholder) {
			SlsSOItem slsSOItem = slsSOItemHolder.getSlsSOItem();
			fullfillOrder(slsSOItem);
		}
		saleOrderManager.doSale(holder);		
	}

	private void fullfillOrder(final SlsSOItem slsSOItem){
		List<StkLotStockQty> qies = lotStockQtyLookup.findLatestQtiesByArtPic(slsSOItem.getArtPic());
		BigDecimal deliveredQty = slsSOItem.getDeliveredQty();
		StkLotStockQty lotStockQty = null; 
		for (StkLotStockQty stkLotStockQty : qies) {
			BigDecimal stockQty = stkLotStockQty.getStockQty();
			if(stockQty.compareTo(deliveredQty)>=0){
				lotStockQty = stkLotStockQty;
				break;
			}
		}
		if(lotStockQty==null && qies.size()>0) lotStockQty = qies.iterator().next(); 
		if(lotStockQty!=null) {
			StkArticleLot2StrgSctn lotSection = articleLot2StrgSctnLookup.findByStrgSectionAndLotPicAndArtPic(lotStockQty.getSection(), lotStockQty.getLotPic(), lotStockQty.getArtPic());
			
			slsSOItem.setArtName(lotSection.getArtName());
			slsSOItem.setLotPic(lotStockQty.getLotPic());
			slsSOItem.setSppuCur(lotSection.getSectionArticleLot().getSlsUnitPrcCur());
			slsSOItem.setSppuPreTax(lotSection.getSectionArticleLot().getSlsUnitPrcPreTax());
			slsSOItem.setVatPct(lotSection.getSectionArticleLot().getSlsVatPct());
			slsSOItem.setSection(lotSection.getSectionName());
		}

	}

}
