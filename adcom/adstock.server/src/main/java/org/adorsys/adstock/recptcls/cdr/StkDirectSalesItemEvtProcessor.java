package org.adorsys.adstock.recptcls.cdr;

import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesEvt;
import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.adorsys.adcshdwr.rest.CdrDsArtItemLookup;
import org.adorsys.adstock.jpa.StkDirectSalesItemHstry;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.rest.StkDirectSalesHstryEJB;
import org.adorsys.adstock.rest.StkDirectSalesItemHstryEJB;
import org.adorsys.adstock.rest.StkLotStockQtyEJB;

/**
 * Check for the incoming of direct sales closed event and 
 * process corresponding inventory items.
 * 
 * @author francis
 *
 */
@Stateless
public class StkDirectSalesItemEvtProcessor {
	@Inject
	private CdrDsArtItemLookup itemEvtDataEJB;
	@Inject
	private StkDirectSalesHstryEJB hstryEJB;
	@Inject
	private StkDirectSalesItemHstryEJB itemHstryEJB;

	@Inject
	private StkLotStockQtyEJB lotStockQtyEJB;

	public void process(String itemEvtDataId, CdrDrctSalesEvt evt) {
		// check if the history object exists.
		Long evtCount = hstryEJB.countById(evt.getId());
		if(evtCount>0) return;
		
		CdrDsArtItem itemEvtData = itemEvtDataEJB.findById(itemEvtDataId);
		if(itemEvtData==null) return;

		// Check if item processed.
		Long itemEvtHstyCount = itemHstryEJB.countById(itemEvtData.getIdentif());
		if(itemEvtHstyCount>0) return;
		
		String artPic = itemEvtData.getArtPic();
		String lotPic = itemEvtData.getLotPic();
		String section = itemEvtData.getSection();

		StkLotStockQty latestQty = lotStockQtyEJB.findLatestQty(artPic, lotPic, section);
		// Lot Stock Qty
		StkLotStockQty lotStockQty = new StkLotStockQty();
		lotStockQty.setArtPic(artPic);
		lotStockQty.setLotPic(lotPic);
		lotStockQty.setSection(section);
		lotStockQty.setQtyDt(evt.getHstryDt());
		lotStockQty.setOrigProcs(evt.getClass().getSimpleName());
		lotStockQty.setOrigProcsNbr(itemEvtData.getBsnsObjNbr());
		if(latestQty!=null){
			lotStockQty.setSeqNbr(latestQty.getSeqNbr()==null?1:latestQty.getSeqNbr()+1);
		} else {
			lotStockQty.setSeqNbr(0);
		}
//		BigDecimal returnedQty = BigDecimalUtils.zeroIfNull(itemEvtData.getReturnedQty());
//		BigDecimal soldQty = BigDecimalUtils.zeroIfNull(itemEvtData.getSoldQty());
//		// +return minus sold.
//		BigDecimal stockQty = BigDecimalUtils.subs(returnedQty, soldQty);
		lotStockQty.setStockQty(itemEvtData.getQtyDlvrd());
		lotStockQty = lotStockQtyEJB.create(lotStockQty);
		
		StkDirectSalesItemHstry hstry = new StkDirectSalesItemHstry();
		evt.copyTo(hstry);
		hstry.setId(itemEvtData.getIdentif());
		hstry.setEntIdentif(itemEvtData.getIdentif());
		hstry.setAddtnlInfo("Direct Sales : " + lotStockQty.getStockQty());
		hstry.setComment(lotStockQty.artPicAndLotPicAndSection());
		itemHstryEJB.create(hstry);
	}

}
