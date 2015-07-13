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
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnEJB;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
import org.adorsys.adstock.rest.StkLotStockQtyEJB;
import org.adorsys.adstock.rest.StkLotStockQtyLookup;
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
public class StkStockQtyCtrlWkr {
	
	private Map<String, StkLotStockQty> cache = new HashMap<String, StkLotStockQty>();
	@Inject
	private StkLotStockQtyLookup lotStockQtyLookup;
	@Inject
	private StkLotStockQtyEJB lotStockQtyEJB;
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
	public void cleaup(StkLotStockQty lotStockQty){
		if(lotStockQty.getQtyDt()==null) return;
		String key = lotStockQty.artPicAndLotPicAndSection();
		if(!cache.containsKey(key))cache.put(key, lotStockQty);
		
		process(key);
	}

	private void process(String artAndLotPicAndSection) {
		StkLotStockQty lotStockQty = cache.get(artAndLotPicAndSection);
		if(DateUtils.addMinutes(new Date(), -3).after(lotStockQty.getQtyDt())) return;
		consolidate(artAndLotPicAndSection);
	}
	
	private void consolidate(String artAndLotPicAndSection) {
		StkLotStockQty lotStockQty = cache.get(artAndLotPicAndSection);
		if(lotStockQty==null) return;
		String artPic = lotStockQty.getArtPic();
		String lotPic = lotStockQty.getLotPic();
		String section = lotStockQty.getSection();
		
		StkLotStockQty base = lotStockQtyLookup.findBase(artPic, lotPic, section);
		if(base==null) return;
		Integer seqNbr = base.getSeqNbr();
		List<StkLotStockQty> picAndSeq = lotStockQtyLookup.findByArtPicAndLotPicAndSectionAndSeqNbr(artPic, lotPic, section, seqNbr);
		BigDecimal baseQty = base.getStockQty();
		String orgiProcess = base.getOrigProcs();
		for (StkLotStockQty lsq : picAndSeq) {
			baseQty = BigDecimalUtils.sum(baseQty,lsq.getStockQty());
			if(seqNbr < lsq.getSeqNbr()) {
				seqNbr = lsq.getSeqNbr();
				orgiProcess = lsq.getOrigProcs();
			}
		}
		Date now = new Date();
		base.setQtyDt(now);
		base.setStockQty(baseQty);
		base.setSeqNbr(seqNbr);
		if(!BigDecimalUtils.greaterZero(baseQty)){
			StkArticleLot2StrgSctn stkArticleLot2StrgSctn = articleLot2StrgSctnLookup.findByStrgSectionAndLotPic(section, lotPic);
			stkArticleLot2StrgSctn.setOutOfStockDt(now);
			if(BigDecimalUtils.isNullOrZero(baseQty)){
				if(StringUtils.isNotBlank(orgiProcess) && "InvInvtry".equals(orgiProcess)){
					stkArticleLot2StrgSctn.setClosedDt(now);
				}
			}
			articleLot2StrgSctnEJB.update(stkArticleLot2StrgSctn);
		}
		try {
			lotStockQtyEJB.update(base);
		} catch(OptimisticLockException e){
			// No opt. Had been taken care of by another thread.
		}
		
		// cleanup
		for (StkLotStockQty lsq : picAndSeq) {
			lotStockQtyEJB.deleteById(lsq.getId());
		}
	}
}
