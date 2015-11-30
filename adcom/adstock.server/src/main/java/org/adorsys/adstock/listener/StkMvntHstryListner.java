package org.adorsys.adstock.listener;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

import org.adorsys.adcore.event.EntityHstryEvent;
import org.adorsys.adstock.jpa.StkArtStockQty;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkLotInSctnStockQty;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.jpa.StkMvnt;
import org.adorsys.adstock.jpa.StkMvntHstry;
import org.adorsys.adstock.rest.StkArtStockQtyEJB;
import org.adorsys.adstock.rest.StkArtStockQtyLookup;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnEJB;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
import org.adorsys.adstock.rest.StkArticleLotEJB;
import org.adorsys.adstock.rest.StkArticleLotLookup;
import org.adorsys.adstock.rest.StkLotInSctnStockQtyEJB;
import org.adorsys.adstock.rest.StkLotInSctnStockQtyLookup;
import org.adorsys.adstock.rest.StkLotStockQtyEJB;
import org.adorsys.adstock.rest.StkLotStockQtyLookup;
import org.adorsys.adstock.rest.StkMvntLookup;
import org.adorsys.adstock.rest.StkSectionLookup;
import org.apache.commons.lang3.StringUtils;

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
	private StkLotInSctnStockQtyEJB lotInSctnStockQtyEJB;
	@EJB
	private StkLotInSctnStockQtyLookup lotInSctnStockQtyLookup;

	@EJB
	private StkArtStockQtyEJB artStockQtyEJB;
	@EJB
	private StkArtStockQtyLookup artStockQtyLookup;

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
		handleArtStockQty(artPic, stkMvnt, hstry);

		StkArticleLot articleLot = articleLotLookup.findByIdentif(StkArticleLot.toId(lotPic));
		if(articleLot==null){
			articleLot = new StkArticleLot();
			stkMvnt.copyTo(articleLot);
			articleLot.setStkgDt(hstry.getHstryDt());
			articleLot.setValueDt(hstry.getHstryDt());
			articleLot = articleLotEJB.create(articleLot);
		}
		
		handleLotStockQty(artPic, lotPic, stkMvnt, hstry);
		
		if(StringUtils.isNotBlank(section))
			handleLotAndSctnStockQty(artPic, lotPic, section, stkMvnt, hstry);
	}
	
	private void handleArtStockQty(String artPic, StkMvnt stkMvnt, StkMvntHstry hstry){
		StkArtStockQty artStockQty = new StkArtStockQty();
		artStockQty.setCntnrIdentif(artPic);
		artStockQty.setQtyDt(hstry.getHstryDt());
		artStockQty.setOrigProcs(stkMvnt.getOrigProcs());
		artStockQty.setOrigProcsNbr(stkMvnt.getOrigProcsNbr());
		artStockQty.setStkMvntIdentif(stkMvnt.getIdentif());
		StkArtStockQty latestQty = artStockQtyLookup.findLatest(artPic);
		if(latestQty!=null){
			artStockQty.setSeqNbr(latestQty.getSeqNbr()==null?1:latestQty.getSeqNbr()+1);
		} else {
			artStockQty.setSeqNbr(0);
		}
		artStockQty.setStockQty(stkMvnt.getTrgtQty());
		artStockQty = artStockQtyEJB.create(artStockQty);		
	}
	private void handleLotStockQty(String artPic, String lotPic, StkMvnt stkMvnt, StkMvntHstry hstry){
		StkLotStockQty latestQty = lotStockQtyLookup.findLatest(artPic, lotPic);
		StkLotStockQty lotStockQty = new StkLotStockQty();
		lotStockQty.setCntnrIdentif(artPic);
		lotStockQty.setLotPic(lotPic);
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
	
	private void handleLotAndSctnStockQty(String artPic, String lotPic, String section, StkMvnt stkMvnt, StkMvntHstry hstry){
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
		
		StkLotInSctnStockQty latestQty = lotInSctnStockQtyLookup.findLatest(artPic, lotPic, section);
		// Lot Stock Qty
		StkLotInSctnStockQty lotInSctnStockQty = new StkLotInSctnStockQty();
		lotInSctnStockQty.setCntnrIdentif(artPic);
		lotInSctnStockQty.setLotPic(lotPic);
		lotInSctnStockQty.setSection(section);
		lotInSctnStockQty.setQtyDt(hstry.getHstryDt());
		lotInSctnStockQty.setOrigProcs(stkMvnt.getOrigProcs());
		lotInSctnStockQty.setOrigProcsNbr(stkMvnt.getOrigProcsNbr());
		lotInSctnStockQty.setStkMvntIdentif(stkMvnt.getIdentif());
		if(latestQty!=null){
			lotInSctnStockQty.setSeqNbr(latestQty.getSeqNbr()==null?1:latestQty.getSeqNbr()+1);
		} else {
			lotInSctnStockQty.setSeqNbr(0);
		}
		lotInSctnStockQty.setStockQty(stkMvnt.getTrgtQty());
		lotInSctnStockQty = lotInSctnStockQtyEJB.create(lotInSctnStockQty);
	}
}
