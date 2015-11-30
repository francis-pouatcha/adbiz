package org.adorsys.adstock.worker;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;

import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkLotInSctnStockQty;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnEJB;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
import org.adorsys.adstock.rest.StkLotInSctnStockQtyEJB;
import org.adorsys.adstock.rest.StkLotInSctnStockQtyLookup;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Controls parallel posting of stock quantity movement and synchronizes them 
 * into the most actual record.
 * 
 * This worker also triggers deletion of older records after consolidation.
 *   
 * @author francis
 *
 */
@Singleton
public class StkLotInSctnStockQtyCtrlWkr {
	
	private Map<String, StkLotInSctnStockQty> cache = new HashMap<String, StkLotInSctnStockQty>();
	@Inject
	private StkLotInSctnStockQtyLookup lotInSctnStockQtyLookup;
	@Inject
	private StkLotInSctnStockQtyEJB lotInSctnStockQtyEJB;
	@Inject
	private StkArticleLot2StrgSctnEJB articleLot2StrgSctnEJB;
	@Inject
	private StkArticleLot2StrgSctnLookup articleLot2StrgSctnLookup;

	/**
	 * This will remove an obsolete record from the data base. This call is performed by the 
	 * Listener of the transaction creating the new record, after that transaction succeeds.
	 */
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void cleaup(StkLotInSctnStockQty lotInSctnStockQty){
		if(lotInSctnStockQty.getQtyDt()==null) return;
		String key = lotInSctnStockQty.artPicAndLotPicAndSection();
		if(!cache.containsKey(key))cache.put(key, lotInSctnStockQty);
		
		process(key);
	}

	private void process(String artAndLotPicAndSection) {
		StkLotInSctnStockQty lotInSctnStockQty = cache.get(artAndLotPicAndSection);
		if(DateUtils.addMinutes(new Date(), -3).after(lotInSctnStockQty.getQtyDt())) return;
		consolidate(artAndLotPicAndSection);
	}
	
	private void consolidate(String artAndLotPicAndSection) {
		StkLotInSctnStockQty lotInSctnStockQty = cache.get(artAndLotPicAndSection);
		if(lotInSctnStockQty==null) return;
		String artPic = lotInSctnStockQty.getCntnrIdentif();
		String lotPic = lotInSctnStockQty.getLotPic();
		String section = lotInSctnStockQty.getSection();
		
		StkLotInSctnStockQty base = lotInSctnStockQtyLookup.findBase(artPic, lotPic, section);
		if(base==null) return;
		BigDecimal baseQty = base.getStockQty();
		String orgiProcess = base.getOrigProcs();
		Date now = new Date();
		base.setQtyDt(now);
		Integer seqNbr = base.getSeqNbr();
		Long countByArtPicAndLotPicAndSectionAndSeqNbr = lotInSctnStockQtyLookup.countByArtPicAndLotPicAndSectionAndSeqNbr(artPic, lotPic, section, seqNbr);
		int max = 100;
		int processed = 0;
		while(processed<countByArtPicAndLotPicAndSectionAndSeqNbr){
			int start = processed;
			processed+=max;
			List<StkLotInSctnStockQty> picAndSeq = lotInSctnStockQtyLookup.findByArtPicAndLotPicAndSectionAndSeqNbr(artPic, lotPic, section, seqNbr, start, max);
			for (StkLotInSctnStockQty lsq : picAndSeq) {
				baseQty = BigDecimalUtils.sum(baseQty,lsq.getStockQty());
				if(seqNbr < lsq.getSeqNbr()) {
					seqNbr = lsq.getSeqNbr();
					orgiProcess = lsq.getOrigProcs();
				}
			}
		}
		base.setStockQty(baseQty);
		base.setSeqNbr(seqNbr);
		base.setOrigProcs(orgiProcess);
		if(!BigDecimalUtils.greaterZero(baseQty)){
			StkArticleLot2StrgSctn stkArticleLot2StrgSctn = articleLot2StrgSctnLookup.findBySectionAndLotPic(section, lotPic);
			stkArticleLot2StrgSctn.setOutOfStockDt(now);
			if(BigDecimalUtils.isNullOrZero(baseQty)){
				if(StringUtils.isNotBlank(orgiProcess) && "InvInvtry".equals(orgiProcess)){
					stkArticleLot2StrgSctn.setClosedDt(now);
				}
			}
			articleLot2StrgSctnEJB.update(stkArticleLot2StrgSctn);
		}
		try {
			lotInSctnStockQtyEJB.update(base);
		} catch(OptimisticLockException e){
			// No opt. Had been taken care of by another thread.
		}
		
		processed = 0;
		while(processed<countByArtPicAndLotPicAndSectionAndSeqNbr){
			int start = processed;
			processed+=max;
			List<StkLotInSctnStockQty> picAndSeq = lotInSctnStockQtyLookup.findByArtPicAndLotPicAndSectionAndSeqNbr(artPic, lotPic, section, seqNbr, start, max);
			// cleanup
			for (StkLotInSctnStockQty lsq : picAndSeq) {
				lotInSctnStockQtyEJB.deleteById(lsq.getId());
			}
		}
	}
}
