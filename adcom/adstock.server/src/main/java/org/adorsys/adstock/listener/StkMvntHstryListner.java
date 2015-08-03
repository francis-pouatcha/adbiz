package org.adorsys.adstock.listener;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

import org.adorsys.adcore.event.EntityHstryEvent;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.jpa.StkMvnt;
import org.adorsys.adstock.jpa.StkMvntHstry;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnEJB;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
import org.adorsys.adstock.rest.StkArticleLotEJB;
import org.adorsys.adstock.rest.StkArticleLotLookup;
import org.adorsys.adstock.rest.StkLotStockQtyEJB;
import org.adorsys.adstock.rest.StkLotStockQtyLookup;
import org.adorsys.adstock.rest.StkMvntLookup;
import org.adorsys.adstock.rest.StkSectionLookup;

@Stateless
public class StkMvntHstryListner {
	
	@EJB
	private StkMvntLookup lookup;
	
	@EJB
	private StkArticleLotLookup articleLotLookup;
	@EJB
	private StkArticleLotEJB articleLotEJB;
	@EJB
	private StkArticleLot2StrgSctnEJB lot2StrgSctnEJB;
	@EJB
	private StkArticleLot2StrgSctnLookup lot2StrgSctnLookup;
	@EJB
	private StkSectionLookup sectionLookup;
	@EJB
	private StkLotStockQtyEJB lotStockQtyEJB;
	@EJB
	private StkLotStockQtyLookup lotStockQtyLookup;
	
	public void handleStkMvntHstry(@Observes @EntityHstryEvent StkMvntHstry hstry){
		String entIdentif = hstry.getEntIdentif();
		StkMvnt stkMvnt = lookup.findByIdentif(entIdentif);
		String artPic = stkMvnt.getArtPic();
		String lotPic = stkMvnt.getLotPic();
		String section = stkMvnt.getSection();
		StkArticleLot articleLot = articleLotLookup.findByIdentif(StkArticleLot.toId(lotPic));
		if(articleLot==null){
			articleLot = new StkArticleLot();
			stkMvnt.copyTo(articleLot);
			articleLot.setStkgDt(hstry.getHstryDt());
			articleLot.setValueDt(hstry.getHstryDt());
			articleLot = articleLotEJB.create(articleLot);
		}
		StkArticleLot2StrgSctn strgSctn = lot2StrgSctnLookup.findBySectionAndLotPic(section, lotPic);
		if(strgSctn==null){
			strgSctn = new StkArticleLot2StrgSctn();
			strgSctn.setArtPic(artPic);
			strgSctn.setLotPic(lotPic);
			strgSctn.setSection(section);
			strgSctn.setQtyDt(hstry.getHstryDt());
			strgSctn.setStockQty(stkMvnt.getTrgtQty());
			strgSctn.setSeqNbr(0);
			strgSctn = lot2StrgSctnEJB.create(strgSctn);
		}

		StkLotStockQty latestQty = lotStockQtyLookup.findLatest(lotPic, section, 0, 1);
		// Lot Stock Qty
		StkLotStockQty lotStockQty = new StkLotStockQty();
		lotStockQty.setArtPic(artPic);
		lotStockQty.setLotPic(lotPic);
		lotStockQty.setSection(section);
		lotStockQty.setQtyDt(hstry.getHstryDt());
		lotStockQty.setOrigProcs(stkMvnt.getOrigProcs());
		lotStockQty.setOrigProcsNbr(stkMvnt.getOrigProcsNbr());
		lotStockQty.setStkMvntIdentif(stkMvnt.getIdentif());
		if(latestQty!=null){
			lotStockQty.setSeqNbr(latestQty.getSeqNbr()==null?1:latestQty.getSeqNbr()+1);
		} else {
			lotStockQty.setSeqNbr(0);
		}
		lotStockQty.setStockQty(stkMvnt.getTrgtQty());
		lotStockQty = lotStockQtyEJB.create(lotStockQty);
	}
}
