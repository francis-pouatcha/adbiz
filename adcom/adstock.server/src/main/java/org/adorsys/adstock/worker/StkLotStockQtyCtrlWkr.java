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
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.rest.StkLotStockQtyEJB;
import org.adorsys.adstock.rest.StkLotStockQtyLookup;
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
public class StkLotStockQtyCtrlWkr {
	
	private Map<String, StkLotStockQty> cache = new HashMap<String, StkLotStockQty>();
	@Inject
	private StkLotStockQtyLookup lotStockQtyLookup;
	@Inject
	private StkLotStockQtyEJB lotStockQtyEJB;

	/**
	 * This will remove an obsolete record from the data base. This call is performed by the 
	 * Listener of the transaction creating the new record, after that transaction succeeds.
	 */
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void cleaup(StkLotStockQty lotStockQty){
		if(lotStockQty.getQtyDt()==null) return;
		String key = lotStockQty.artPicAndLotPic();
		if(!cache.containsKey(key))cache.put(key, lotStockQty);
		process(key);
	}

	private void process(String artAndLotPic) {
		StkLotStockQty lotStockQty = cache.get(artAndLotPic);
		if(DateUtils.addMinutes(new Date(), -3).after(lotStockQty.getQtyDt())) return;
		consolidate(artAndLotPic);
	}
	
	private void consolidate(String artAndLotPic) {
		Date now = new Date();
		StkLotStockQty lotStockQty = cache.get(artAndLotPic);
		if(lotStockQty==null) return;
		String artPic = lotStockQty.getCntnrIdentif();
		String lotPic = lotStockQty.getLotPic();
		
		StkLotStockQty base = lotStockQtyLookup.findBase(artPic, lotPic);
		if(base==null) return;
		BigDecimal baseQty = base.getStockQty();
		Integer seqNbr = base.getSeqNbr();
		String orgiProcess = base.getOrigProcs();
		base.setQtyDt(now);

		Long countByArtPicAndLotPicAndSeqNbr = lotStockQtyLookup.countByArtPicAndLotPicAndSeqNbr(artPic, lotPic, seqNbr);
		int max = 100;
		int processed = 0;
		while(processed<countByArtPicAndLotPicAndSeqNbr){
			int start = processed;
			processed += max;
			List<StkLotStockQty> picAndSeq = lotStockQtyLookup.findByArtPicAndLotPicAndSeqNbr(artPic, lotPic, seqNbr, start, max);
			for (StkLotStockQty lsq : picAndSeq) {
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
			lotStockQtyEJB.update(base);
		} catch(OptimisticLockException e){
			// No opt. Had been taken care of by another thread.
		}
		
		processed = 0;
		while(processed<countByArtPicAndLotPicAndSeqNbr){
			int start = processed;
			processed += max;
			List<StkLotStockQty> picAndSeq = lotStockQtyLookup.findByArtPicAndLotPicAndSeqNbr(artPic, lotPic, seqNbr, start, max);
			// cleanup
			for (StkLotStockQty lsq : picAndSeq) {
				lotStockQtyEJB.deleteById(lsq.getId());
			}
		}
	}
}
