package org.adorsys.adstock.listener;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

import org.adorsys.adcore.event.EntityHstryEvent;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adstock.jpa.StkAbstStockQty;
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
		Date expirDt = stkMvnt.getExpirDt();

		// handle the stock of all article with this pic 
		handleArtStockQty(artPic, stkMvnt, hstry);

		// create the article lot.
		if(!Boolean.TRUE.equals(stkMvnt.getProfmt())){
			StkArticleLot articleLot = articleLotLookup.findByIdentif(StkArticleLot.toId(lotPic));
			if(articleLot==null){
				articleLot = new StkArticleLot();
				stkMvnt.copyTo(articleLot);
				articleLot.setStkgDt(hstry.getHstryDt());
				articleLot.setValueDt(hstry.getHstryDt());
				articleLot = articleLotEJB.create(articleLot);
			}
		}
		
		// Handle the stock of the article lot.
		handleLotStockQty(artPic, lotPic, stkMvnt, hstry);
		
		if(StringUtils.isNotBlank(section))
			handleLotAndSctnStockQty(artPic, lotPic, expirDt, section, stkMvnt, hstry);
	}
	
	private void handleArtStockQty(String artPic, StkMvnt stkMvnt, StkMvntHstry hstry){
		// No stocking for proformat invoice.
		if(Boolean.TRUE.equals(stkMvnt.getProfmt())) return;
		
		StkArtStockQty artStockQty = new StkArtStockQty();
		artStockQty.setCntnrIdentif(artPic);
		List<StkArtStockQty> latestQtyList = null;
		
		StkArtStockQty latestQty = artStockQtyLookup.findLatest(artPic);
		if(latestQty!=null){
			latestQtyList = artStockQtyLookup.findByArtPicAndSeqNbr(artPic, latestQty.getSeqNbr());
		} else {
			latestQtyList = Collections.emptyList();
		}
		// Handle the stock mvnt
		handleStockMvnt(artStockQty, latestQtyList, stkMvnt, hstry);

		artStockQtyEJB.create(artStockQty);		
	}
	
	private void handleLotStockQty(String artPic, String lotPic, StkMvnt stkMvnt, StkMvntHstry hstry){
		// No stocking for proformat invoice.
		if(Boolean.TRUE.equals(stkMvnt.getProfmt())) return;

		StkLotStockQty lotStockQty = new StkLotStockQty();
		lotStockQty.setCntnrIdentif(artPic);
		lotStockQty.setLotPic(lotPic);
		StkLotStockQty latestQty = lotStockQtyLookup.findLatest(artPic, lotPic);
		List<StkLotStockQty> latestQtyList = null;
		if(latestQty!=null){
			latestQtyList = lotStockQtyLookup.findByArtPicAndLotPicAndSeqNbr(artPic, lotPic, latestQty.getSeqNbr());
		} else {
			latestQtyList = Collections.emptyList();
		}
		// Handle the stock mvnt
		handleStockMvnt(lotStockQty, latestQtyList, stkMvnt, hstry);

		lotStockQtyEJB.create(lotStockQty);
	}
	
	private void handleLotAndSctnStockQty(String artPic, String lotPic, Date expirDt, String section, StkMvnt stkMvnt, StkMvntHstry hstry){
		// No stocking for proformat invoice.
		if(Boolean.TRUE.equals(stkMvnt.getProfmt())) return;

		// Lot Stock Qty
		StkLotInSctnStockQty lotInSctnStockQty = new StkLotInSctnStockQty();
		lotInSctnStockQty.setCntnrIdentif(artPic);
		lotInSctnStockQty.setLotPic(lotPic);
		lotInSctnStockQty.setSection(section);
		StkLotInSctnStockQty latestQty = lotInSctnStockQtyLookup.findLatest(artPic, lotPic, section);
		List<StkLotInSctnStockQty> latestQtyList = null;
		if(latestQty!=null){
			latestQtyList = lotInSctnStockQtyLookup.findByArtPicAndLotPicAndSectionAndSeqNbr(artPic, lotPic, section, latestQty.getSeqNbr());
		} else {
			latestQtyList = Collections.emptyList();
		}
		// Handle the stock mvnt
		handleStockMvnt(lotInSctnStockQty, latestQtyList, stkMvnt, hstry);

		lotInSctnStockQty = lotInSctnStockQtyEJB.create(lotInSctnStockQty);
	
		StkArticleLot2StrgSctn strgSctn = lot2StrgSctnLookup.findBySectionAndLotPic(section, lotPic);
		if(strgSctn==null){
			strgSctn = new StkArticleLot2StrgSctn();
			strgSctn.setArtPic(artPic);
			strgSctn.setLotPic(lotPic);
			strgSctn.setExpirDt(expirDt);
			strgSctn.setSection(section);
			strgSctn.setQtyDt(hstry.getHstryDt());
			strgSctn.setStockQty(lotInSctnStockQty.getStockQty());
			strgSctn.setSeqNbr(0);
			strgSctn = lot2StrgSctnEJB.create(strgSctn);
		}
	}
	
	private void handleStockMvnt(final StkAbstStockQty stockQty, List<? extends StkAbstStockQty> latestQties, StkMvnt stkMvnt, StkMvntHstry hstry){
		stockQty.setQtyDt(hstry.getHstryDt());
		stockQty.setOrigProcs(stkMvnt.getOrigProcs());
		stockQty.setOrigProcsNbr(stkMvnt.getOrigProcsNbr());
		stockQty.setStkMvntIdentif(stkMvnt.getIdentif());
		stockQty.setStkMvntQty(stkMvnt.getTrgtQty());
		
		StkAbstStockQty latestQty = null;
		if(!latestQties.isEmpty()){
			latestQty = latestQties.iterator().next();
			stockQty.setParentRcrd(latestQty.getId());
		}

		BigDecimal reservedBeforeMe = latestQty==null?BigDecimal.ZERO:latestQty.getPrcdngRsvdQty();
		BigDecimal stockBeforeMe = latestQty==null?BigDecimal.ZERO:latestQty.getPrcdngStkQty();
		
		for (StkAbstStockQty stkAbstStockQty : latestQties) {
			reservedBeforeMe = BigDecimalUtils.sum(reservedBeforeMe,stkAbstStockQty.getRsvrdQty());
			stockBeforeMe = BigDecimalUtils.sum(stockBeforeMe,stkAbstStockQty.getStockQty());
		}		
		
		stockQty.setPrcdngStkQty(stockBeforeMe);
		stockQty.setPrcdngRsvdQty(reservedBeforeMe);
		stockQty.setSeqNbr(latestQty==null?0:latestQty.getSeqNbr()+1);
		
		if (Boolean.TRUE.equals(stkMvnt.getRsvd())){
			stockQty.setRsvrdQty(BigDecimalUtils.sum(reservedBeforeMe,stkMvnt.getTrgtQty()));
		} else {
			stockQty.setStockQty(BigDecimalUtils.sum(stockBeforeMe,stkMvnt.getTrgtQty()));
		}
	}
}
