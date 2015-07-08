package org.adorsys.adstock.recptcls.sls;

import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adsales.jpa.SlsInvceEvt;
import org.adorsys.adsales.jpa.SlsInvceItem;
import org.adorsys.adsales.rest.SlsInvceItemLookup;
import org.adorsys.adstock.jpa.StkInvceItemHstry;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.rest.StkInvceHstryEJB;
import org.adorsys.adstock.rest.StkInvceItemHstryEJB;
import org.adorsys.adstock.rest.StkLotStockQtyEJB;

/**
 * Check for the incoming of direct sales closed event and 
 * process corresponding inventory items.
 * 
 * @author francis
 *
 */
@Stateless
public class SlsInvceItemEvtProcessor {
	@Inject
	private SlsInvceItemLookup itemEvtDataEJB;
	@Inject
	private StkInvceHstryEJB hstryEJB;
	@Inject
	private StkInvceItemHstryEJB itemHstryEJB;

	@Inject
	private StkLotStockQtyEJB lotStockQtyEJB;

	public void process(String itemEvtDataId, SlsInvceEvt evt) {
		// check if the history object exists.
		Long evtCount = hstryEJB.countById(evt.getId());
		if(evtCount>0) return;
		
		SlsInvceItem itemEvtData = itemEvtDataEJB.findById(itemEvtDataId);
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
		lotStockQty.setOrigProcsNbr(itemEvtData.getInvNbr());
		if(latestQty!=null){
			lotStockQty.setSeqNbr(latestQty.getSeqNbr()==null?1:latestQty.getSeqNbr()+1);
		} else {
			lotStockQty.setSeqNbr(0);
		}
		// minus qty.
		BigDecimal stockQty = BigDecimalUtils.negate(itemEvtData.getQty());
		lotStockQty.setStockQty(stockQty);
		lotStockQty = lotStockQtyEJB.create(lotStockQty);
		
		StkInvceItemHstry hstry = new StkInvceItemHstry();
		evt.copyTo(hstry);
		hstry.setId(itemEvtData.getIdentif());
		hstry.setEntIdentif(itemEvtData.getIdentif());
		hstry.setAddtnlInfo("Sales : " + lotStockQty.getStockQty());
		hstry.setComment(lotStockQty.artPicAndLotPicAndSection());
		itemHstryEJB.create(hstry);
	}

}
