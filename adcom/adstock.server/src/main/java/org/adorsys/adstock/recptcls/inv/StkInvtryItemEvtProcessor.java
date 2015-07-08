package org.adorsys.adstock.recptcls.inv;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemHeader;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryEvt;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.repo.InvInvtryItemRepository;
import org.adorsys.adinvtry.repo.InvInvtryRepository;
import org.adorsys.adstock.jpa.StkAbstractArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkInvtryItemHstry;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnEJB;
import org.adorsys.adstock.rest.StkArticleLotEJB;
import org.adorsys.adstock.rest.StkInvtryItemHstryEJB;
import org.adorsys.adstock.rest.StkLotStockQtyEJB;
import org.adorsys.adstock.rest.StkSectionEJB;

/**
 * Check for the incoming of inventory closed event and 
 * process corresponding inventory items.
 * 
 * @author francis
 *
 */
@Stateless
public class StkInvtryItemEvtProcessor {

	@Inject
	private InvInvtryRepository invInvtryRepository;
	@Inject
	private InvInvtryItemRepository invInvtryItemRepository;

	@Inject
	private StkLotStockQtyEJB lotStockQtyEJB;
	@Inject
	private StkArticleLot2StrgSctnEJB articleLot2StrgSctnEJB;
	@Inject
	private StkInvtryItemHstryEJB invtryItemHstryEJB;
	@Inject
	private StkArticleLotEJB articleLotEJB;
	@Inject
	private StkSectionEJB sectionEJB;

	public void process(String itemEvtDataId, InvInvtryEvt invtryEvt) {
		InvInvtryItem itemEvtData = invInvtryItemRepository.findBy(itemEvtDataId);
		if(itemEvtData==null) return;

		InvInvtry invtryEvtData = invInvtryRepository.findBy(itemEvtData.getBsnsObjNbr());
		if(invtryEvtData==null) return;

		StkInvtryItemHstry hstry = invtryItemHstryEJB.findById(itemEvtData.getIdentif());
		if(hstry!=null) return;// processed.
		
		String artPic = itemEvtData.getArtPic();
		String lotPic = itemEvtData.getLotPic();
		String section = itemEvtData.getSection();
		
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
//			stkArticleLot.setPppuHT(itemEvtData.getPppuPT());
//			stkArticleLot.setPurchRtrnDays(itemEvtData.getPurchRtrnDays());
//			stkArticleLot.setPurchWrntyDys(itemEvtData.getPurchWrntyDys());
//			stkArticleLot.setSalesRtrnDays(itemEvtData.getSalesRtrnDays());
//			stkArticleLot.setSalesWrntyDys(itemEvtData.getSalesWrntyDys());
//			stkArticleLot.setSppuCur(itemEvtData.getSppuCur());
//			stkArticleLot.setSppuHT(itemEvtData.getSppuPT());
			stkArticleLot.setStkgDt(invtryEvt.getHstryDt());
			stkArticleLot.setValueDt(invtryEvt.getHstryDt());
//			stkArticleLot.setSupplierPic(itemEvtData.getSupplierPic());
//			stkArticleLot.setSupplier(itemEvtData.getSupplier());
//			stkArticleLot.setVatPurchPct(itemEvtData.getVatPurchPct());
//			stkArticleLot.setVatSalesPct(itemEvtData.getVatSalesPct());
			stkArticleLot = articleLotEJB.create(stkArticleLot);
		} else {
			CoreAbstBsnsItemHeader header = new CoreAbstBsnsItemHeader();
			header.copyFrom(stkArticleLot);
			itemEvtData.copyTo(stkArticleLot);
			header.copyTo(stkArticleLot);// restore header info.
//			stkArticleLot.setMinSppuHT(itemEvtData.getMinSppuHT());
//			stkArticleLot.setPppuCur(itemEvtData.getPppuCur());
//			stkArticleLot.setPppuHT(itemEvtData.getPppuPT());
//			stkArticleLot.setPurchRtrnDays(itemEvtData.getPurchRtrnDays());
//			stkArticleLot.setPurchWrntyDys(itemEvtData.getPurchWrntyDys());
//			stkArticleLot.setSalesRtrnDays(itemEvtData.getSalesRtrnDays());
//			stkArticleLot.setSalesWrntyDys(itemEvtData.getSalesWrntyDys());
//			stkArticleLot.setSppuCur(itemEvtData.getSppuCur());
//			stkArticleLot.setSppuHT(itemEvtData.getSppuPT());
			stkArticleLot.setStkgDt(invtryEvt.getHstryDt());
			stkArticleLot.setValueDt(invtryEvt.getHstryDt());
//			stkArticleLot.setSupplierPic(itemEvtData.getSupplierPic());
//			stkArticleLot.setSupplier(itemEvtData.getSupplier());
//			stkArticleLot.setVatPurchPct(itemEvtData.getVatPurchPct());
//			stkArticleLot.setVatSalesPct(itemEvtData.getVatSalesPct());
			stkArticleLot = articleLotEJB.update(stkArticleLot);
		}		
		

		StkArticleLot2StrgSctn strgSctn = articleLot2StrgSctnEJB.findByStrgSectionAndLotPicAndArtPic(section, lotPic, artPic);
		if(strgSctn==null){
			strgSctn = new StkArticleLot2StrgSctn();
			strgSctn.setArtPic(artPic);
			strgSctn.setLotPic(lotPic);
			strgSctn.setStrgSection(section);
			strgSctn.setArtName(itemEvtData.getArtName());
			StkSection stkSection = sectionEJB.findByIdentif(section, new Date());
			strgSctn.setSectionName(stkSection.getName());
			strgSctn = articleLot2StrgSctnEJB.create(strgSctn);
		}

		StkLotStockQty latestQty = lotStockQtyEJB.findLatestQty(artPic, lotPic, section);
		// Lot Stock Qty
		StkLotStockQty lotStockQty = new StkLotStockQty();
		lotStockQty.setArtPic(artPic);
		lotStockQty.setLotPic(lotPic);
		lotStockQty.setSection(section);
		lotStockQty.setQtyDt(invtryEvt.getHstryDt());
		lotStockQty.setOrigProcs(invtryEvt.getClass().getSimpleName());
		lotStockQty.setOrigProcsNbr(itemEvtData.getBsnsObjNbr());
		if(latestQty!=null){
			lotStockQty.setSeqNbr(latestQty.getSeqNbr()==null?1:latestQty.getSeqNbr()+1);
		} else {
			lotStockQty.setSeqNbr(0);
		}
		lotStockQty.setStockQty(BigDecimalUtils.negate(itemEvtData.getGap()));
		lotStockQty = lotStockQtyEJB.create(lotStockQty);
		
		hstry = new StkInvtryItemHstry();
		invtryEvt.copyTo(hstry);
		hstry.setId(itemEvtData.getIdentif());
		hstry.setEntIdentif(itemEvtData.getIdentif());
		hstry.setAddtnlInfo("Inventory Gap : " + lotStockQty.getStockQty());
		hstry.setComment(lotStockQty.artPicAndLotPicAndSection());
		invtryItemHstryEJB.create(hstry);
	}

}
