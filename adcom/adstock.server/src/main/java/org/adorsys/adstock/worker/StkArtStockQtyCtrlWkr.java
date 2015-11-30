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
import org.adorsys.adstock.jpa.StkArtStockQty;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnEJB;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
import org.adorsys.adstock.rest.StkArtStockQtyEJB;
import org.adorsys.adstock.rest.StkArtStockQtyLookup;
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
public class StkArtStockQtyCtrlWkr {
	
	private Map<String, StkArtStockQty> cache = new HashMap<String, StkArtStockQty>();
	@Inject
	private StkArtStockQtyLookup artStockQtyLookup;
	@Inject
	private StkArtStockQtyEJB artStockQtyEJB;

	/**
	 * This will remove an obsolete record from the data base. This call is performed by the 
	 * Listener of the transaction creating the new record, after that transaction succeeds.
	 */
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void cleaup(StkArtStockQty artStockQty){
		if(artStockQty.getQtyDt()==null) return;
		String key = artStockQty.getCntnrIdentif();
		if(!cache.containsKey(key))cache.put(key, artStockQty);
		
		process(key);
	}

	private void process(String artPic) {
		StkArtStockQty artStockQty = cache.get(artPic);
		if(DateUtils.addMinutes(new Date(), -3).after(artStockQty.getQtyDt())) return;
		consolidate(artPic);
	}
	
	private void consolidate(String artPic) {
		StkArtStockQty artStockQty = cache.get(artPic);
		if(artStockQty==null) return;
		
		StkArtStockQty base = artStockQtyLookup.findBase(artPic);
		if(base==null) return;
		BigDecimal baseQty = base.getStockQty();
		String orgiProcess = base.getOrigProcs();
		Integer seqNbr = base.getSeqNbr();
		Date now = new Date();
		base.setQtyDt(now);
		Long countByArtPicAndSeqNbr = artStockQtyLookup.countByArtPicAndSeqNbr(artPic, seqNbr);
		int max = 100;
		int processed = 0;
		while(processed<countByArtPicAndSeqNbr){
			int start = processed;
			processed+=max;
			List<StkArtStockQty> picAndSeq = artStockQtyLookup.findByArtPicAndSeqNbr(artPic, seqNbr, start, max);
			for (StkArtStockQty lsq : picAndSeq) {
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
		try {
			artStockQtyEJB.update(base);
		} catch(OptimisticLockException e){
			// No opt. Had been taken care of by another thread.
		}
		
		processed = 0;
		while(processed<countByArtPicAndSeqNbr){
			int start = processed;
			processed+=max;
			List<StkArtStockQty> picAndSeq = artStockQtyLookup.findByArtPicAndSeqNbr(artPic, seqNbr, start, max);
			// cleanup
			for (StkArtStockQty lsq : picAndSeq) {
				artStockQtyEJB.deleteById(lsq.getId());
			}
		}
	}
}
