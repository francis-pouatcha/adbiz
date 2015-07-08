package org.adorsys.adstock.recptcls.pcrmt;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemHeader;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvt;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctn;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryItemEJB;
import org.adorsys.adstock.jpa.StkAbstractArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2Ou;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkDlvryItemHstry;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.rest.StkArticleLot2OuEJB;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnEJB;
import org.adorsys.adstock.rest.StkArticleLotEJB;
import org.adorsys.adstock.rest.StkDlvryItemHstryEJB;
import org.adorsys.adstock.rest.StkLotStockQtyEJB;
import org.adorsys.adstock.rest.StkSectionEJB;

/**
 * Check for the incoming of delivery closed event and 
 * process corresponding delivery items.
 * 
 * @author francis
 *
 */
@Stateless
public class StkDeliveryItemEvtProcessor {
	@Inject
	private PrcmtDeliveryEJB evtDataEJB;
	@Inject
	private PrcmtDlvryItemEJB itemEvtDataEJB;
	@Inject
	private StkDlvryItemHstryEJB dlvryItemHstryEJB;
	@Inject
	private StkArticleLotEJB articleLotEJB;
	@Inject
	private StkLotStockQtyEJB lotStockQtyEJB;
	@Inject
	private StkArticleLot2OuEJB articleLot2OuEJB;
	@Inject
	private StkArticleLot2StrgSctnEJB articleLot2StrgSctnEJB;
	@Inject
	private StkSectionEJB sectionEJB;

	public void process(String itemEvtDataId, PrcmtDeliveryEvt deliveryEvt) {
		PrcmtDlvryItem itemEvtData = itemEvtDataEJB.findById(itemEvtDataId);
		if(itemEvtData==null) return;
		
		PrcmtDelivery deliveryEvtData = evtDataEJB.findById(itemEvtData.getBsnsObjNbr());
		if(deliveryEvtData==null) return;

		StkDlvryItemHstry hstry = dlvryItemHstryEJB.findById(itemEvtData.getItemNbr());
		if(hstry!=null) return;
		
		String lotPic = itemEvtData.getLotPic();
		StkArticleLot stkArticleLot = articleLotEJB.findByIdentif(StkAbstractArticleLot.toId(lotPic));
		if(stkArticleLot==null){
			stkArticleLot = new StkArticleLot();
			itemEvtData.copyTo(stkArticleLot);
			CoreAbstBsnsItemHeader header = new CoreAbstBsnsItemHeader();
			header.copyTo(stkArticleLot);// cleanup header info.

//			stkArticleLot.setArtPic(artPic);
//			stkArticleLot.setExpirDt(itemEvtData.getExpirDt());
//			stkArticleLot.setLotPic(lotPic);
//			stkArticleLot.setMinSppuHT(itemEvtData.getMinSppuHT());
//			stkArticleLot.setPppuCur(itemEvtData.getPppuCur());
//			stkArticleLot.setPppuHT(itemEvtData.getPppuPreTax());
//			stkArticleLot.setPurchRtrnDays(itemEvtData.getPurchRtrnDays());
//			stkArticleLot.setPurchWrntyDys(itemEvtData.getPurchWrntyDys());
//			stkArticleLot.setSalesRtrnDays(itemEvtData.getSalesRtrnDays());
//			stkArticleLot.setSalesWrntyDys(itemEvtData.getSalesWrntyDys());
//			stkArticleLot.setSppuCur(itemEvtData.getSppuCur());
//			stkArticleLot.setSppuHT(itemEvtData.getSppuPreTax());
			stkArticleLot.setStkgDt(deliveryEvt.getHstryDt());
			stkArticleLot.setValueDt(deliveryEvt.getHstryDt());
//			stkArticleLot.setSupplierPic(itemEvtData.getSupplierPic());
//			stkArticleLot.setSupplier(itemEvtData.getSupplier());
//			stkArticleLot.setVatPurchPct(itemEvtData.getVatPct());
//			stkArticleLot.setVatSalesPct(itemEvtData.getVatSalesPct());
			stkArticleLot = articleLotEJB.create(stkArticleLot);
		} else {
			CoreAbstBsnsItemHeader header = new CoreAbstBsnsItemHeader();
			header.copyFrom(stkArticleLot);
			itemEvtData.copyTo(stkArticleLot);
			header.copyTo(stkArticleLot);// restore header info.
//			stkArticleLot.setMinSppuHT(itemEvtData.getMinSppuHT());
//			stkArticleLot.setPppuCur(itemEvtData.getPppuCur());
//			stkArticleLot.setPppuHT(itemEvtData.getPppuPreTax());
//			stkArticleLot.setPurchRtrnDays(itemEvtData.getPurchRtrnDays());
//			stkArticleLot.setPurchWrntyDys(itemEvtData.getPurchWrntyDys());
//			stkArticleLot.setSalesRtrnDays(itemEvtData.getSalesRtrnDays());
//			stkArticleLot.setSalesWrntyDys(itemEvtData.getSalesWrntyDys());
//			stkArticleLot.setSppuCur(itemEvtData.getSppuCur());
//			stkArticleLot.setSppuHT(itemEvtData.getSppuPreTax());
			stkArticleLot.setStkgDt(deliveryEvt.getHstryDt());
			stkArticleLot.setValueDt(deliveryEvt.getHstryDt());
//			stkArticleLot.setSupplierPic(itemEvtData.getSupplierPic());
//			stkArticleLot.setSupplier(itemEvtData.getSupplier());
//			stkArticleLot.setVatPurchPct(itemEvtData.getVatPct());
//			stkArticleLot.setVatSalesPct(itemEvtData.getVatSalesPct());
			stkArticleLot = articleLotEJB.update(stkArticleLot);
		}
				
		List<PrcmtDlvryItem2Ou> ouEvtDataList = itemEvtDataEJB.listDlvryItem2Ou(itemEvtData.getItemNbr());
		for (PrcmtDlvryItem2Ou evtData : ouEvtDataList) {
			StkArticleLot2Ou ou = new StkArticleLot2Ou();
			ou.setArtPic(stkArticleLot.getArtPic());
			ou.setLotPic(stkArticleLot.getLotPic());
			ou.setOrgUnit(evtData.getRcvngOrgUnit());
			ou.setQty(BigDecimalUtils.sum(evtData.getFreeQty(), evtData.getQtyDlvrd()));
			articleLot2OuEJB.create(ou);
		}
		
		List<PrcmtDlvryItem2StrgSctn> strgSctnEvtDataList = itemEvtDataEJB.listDlvryItem2StrgSctn(itemEvtData.getItemNbr());
		BigDecimal stored = BigDecimal.ZERO;
		for (PrcmtDlvryItem2StrgSctn strgSctnEvtData : strgSctnEvtDataList) {
			StkArticleLot2StrgSctn strgSctn = new StkArticleLot2StrgSctn();
			strgSctn.setArtPic(stkArticleLot.getArtPic());
			strgSctn.setLotPic(stkArticleLot.getLotPic());
			strgSctn.setStrgSection(strgSctnEvtData.getStrgSection());
			StkSection stkSection = sectionEJB.findByIdentif(strgSctnEvtData.getStrgSection(), new Date());
			strgSctn.setSectionName(stkSection.getName());
			strgSctn.setArtName(itemEvtData.getArtName());
			articleLot2StrgSctnEJB.create(strgSctn);

			// Lot Stock Qty
			StkLotStockQty lotStockQty = new StkLotStockQty();
			lotStockQty.setArtPic(itemEvtData.getArtPic());
			lotStockQty.setLotPic(itemEvtData.getLotPic());
			lotStockQty.setSection(strgSctnEvtData.getStrgSection());
			lotStockQty.setQtyDt(deliveryEvt.getHstryDt());
			lotStockQty.setSeqNbr(0);
			lotStockQty.setStockQty(strgSctnEvtData.getQtyStrd());
			lotStockQty.setOrigProcs(deliveryEvt.getClass().getSimpleName());
			lotStockQty.setOrigProcsNbr(itemEvtData.getBsnsObjNbr());
			stored = BigDecimalUtils.sum(stored, strgSctnEvtData.getQtyStrd());
			lotStockQtyEJB.create(lotStockQty);
		}
		
		hstry = new StkDlvryItemHstry();
		deliveryEvt.copyTo(hstry);
		hstry.setId(itemEvtData.getItemNbr());
		hstry.setEntIdentif(itemEvtData.getItemNbr());
		hstry.setAddtnlInfo("stored : " + stored);
		hstry.setComment(stkArticleLot.getLotPic());
		dlvryItemHstryEJB.create(hstry);
	}
}
