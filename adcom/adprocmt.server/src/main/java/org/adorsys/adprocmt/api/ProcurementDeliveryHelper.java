package org.adorsys.adprocmt.api;

import static org.adorsys.adprocmt.api.PrcmtDlvryItemExcel.DELIVERED_PREFIX;
import static org.adorsys.adprocmt.api.PrcmtDlvryItemExcel.ENTRY_SEPARATOR;
import static org.adorsys.adprocmt.api.PrcmtDlvryItemExcel.FREE_PREFIX;
import static org.adorsys.adprocmt.api.PrcmtDlvryItemExcel.QTY_END;
import static org.adorsys.adprocmt.api.PrcmtDlvryItemExcel.QTY_START;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adcore.utils.CalendarUtil;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctn;
import org.adorsys.adprocmt.rest.PrcmtDeliveryLookup;
import org.adorsys.adprocmt.rest.PrcmtDlvry2OuLookup;
import org.adorsys.adprocmt.rest.PrcmtDlvryItem2OuEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryItem2OuLookup;
import org.adorsys.adprocmt.rest.PrcmtDlvryItem2StrgSctnEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryItem2StrgSctnLookup;
import org.adorsys.adprocmt.rest.PrcmtDlvryItemLookup;
import org.adorsys.adstock.jpa.StkArtLotMgmnt;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkLotInSctnStockQty;
import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.rest.StkArt2SectionLookup;
import org.adorsys.adstock.rest.StkArtLotMgmntLookup;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
import org.adorsys.adstock.rest.StkArticleLotLookup;
import org.adorsys.adstock.rest.StkLotInSctnStockQtyLookup;
import org.adorsys.adstock.rest.StkSectionLookup;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

@Stateless
public class ProcurementDeliveryHelper {
	
	@Inject
	private StkArticleLot2StrgSctnLookup articleLot2StrgSctnLookup;
	@Inject
	private StkArt2SectionLookup art2SectionLookup;
	@Inject
	private PrcmtDlvryItem2StrgSctnEJB dlvryItem2StrgSctnEJB;
	@Inject
	private PrcmtDlvryItem2OuEJB dlvryItem2OuEJB;
	@Inject
	private PrcmtDlvryItem2OuLookup dlvryItem2OuLookup;
	
	@Inject
	private StkArticleLotLookup articleLotLookup;
	@Inject
	private StkArtLotMgmntLookup artLotMgmntLookup;
	
	@Inject
	private StkSectionLookup stkSectionLookup;
	@EJB
	private PrcmtDlvry2OuLookup dlvry2OuLookup;
	@Inject
	private PrcmtDeliveryLookup lookup;
	@Inject
	private PrcmtDlvryItemLookup itemLookup;
	@EJB
	private StkLotInSctnStockQtyLookup lotInSctnStockQtyLookup;
	@EJB
	private PrcmtDlvryItem2StrgSctnLookup dlvryItem2StrgSctnLookup;
	
	public List<PrcmtDlvryItem2StrgSctn> processSectionFromItem(final PrcmtDlvryItem itemExcel, Date now){
		List<PrcmtDlvryItem2StrgSctn> item2SectionList = new ArrayList<PrcmtDlvryItem2StrgSctn>();
		// If no distribution yet and a line comes with a section code. All articles will be stored in that section.
		if(item2SectionList.isEmpty() && StringUtils.isNotBlank(itemExcel.getSection())){// No section defined. Discover some.
			PrcmtDlvryItem2StrgSctn item2Section = createPrcmtDlvryItem2StrgSctn(itemExcel, now, itemExcel.getSection(), itemExcel.getTrgtQty());
			item2SectionList.add(item2Section);
		}

		return item2SectionList;
	}

	public List<PrcmtDlvryItem2StrgSctn> discoverSection(final PrcmtDlvryItem itemExcel, Date now){
		List<PrcmtDlvryItem2StrgSctn> item2SectionList = new ArrayList<PrcmtDlvryItem2StrgSctn>();

		PrcmtDelivery delivery = lookup.findByIdentifThrowException(itemExcel.getCntnrIdentif());

		// Discover a section.
		String foundSection = art2SectionLookup.discoverSection(itemExcel.getArtPic(), delivery.getSection());
		if(StringUtils.isBlank(foundSection)) {
			// Try to discover a section in this location at the lot level.
			StkArticleLot2StrgSctn articleLot2StrgSctn = articleLot2StrgSctnLookup.discoverSection(itemExcel.getArtPic(), delivery.getSection());
			if(articleLot2StrgSctn!=null)foundSection = articleLot2StrgSctn.getSection();
		}
		
		if(StringUtils.isNotBlank(foundSection)) {
			PrcmtDlvryItem2StrgSctn item2Section = createPrcmtDlvryItem2StrgSctn(itemExcel, now, foundSection, itemExcel.getTrgtQty());
			item2SectionList.add(item2Section);		
			itemExcel.setSection(foundSection);
//		} else {
//			throw new IllegalStateException("No section found to store article: " + itemExcel.getArtPic());
		}

		return item2SectionList;
	}

	public List<PrcmtDlvryItem2Ou> processOrgUnitsFromItem(PrcmtDlvryItem itemExcel, Date now) {
		List<PrcmtDlvryItem2Ou> item2Ous = new ArrayList<PrcmtDlvryItem2Ou>();
		
		if(StringUtils.isBlank(itemExcel.getOrgUnit())) return item2Ous;
		
		PrcmtDlvryItem2Ou item2Ou = new PrcmtDlvryItem2Ou();
		item2Ou.setFreeQty(itemExcel.getFreeQty());
		item2Ou.setQtyDlvrd(itemExcel.getTrgtQty());
		item2Ou.setRcvngOrgUnit(itemExcel.getOrgUnit());
		item2Ou.setValueDt(now);
		item2Ous.add(item2Ou);

		return item2Ous;
	}

	
	public List<PrcmtDlvryItem2Ou> processOrgUnitsFromDlvry(PrcmtDlvryItem itemExcel, Date now) {
		List<PrcmtDlvryItem2Ou> item2Ous = new ArrayList<PrcmtDlvryItem2Ou>();
		Long ouCount = dlvry2OuLookup.countByCntnrIdentif(itemExcel.getCntnrIdentif());
		if(ouCount>0){
			List<PrcmtDlvry2Ou> ous = dlvry2OuLookup.findByCntnrIdentif(itemExcel.getCntnrIdentif(), 0, ouCount.intValue());
			List<PrcmtDlvry2Ou> sortedOus = new ArrayList<PrcmtDlvry2Ou>(ous);
			Collections.sort(sortedOus, new Comparator<PrcmtDlvry2Ou>() {
				@Override
				public int compare(PrcmtDlvry2Ou o1, PrcmtDlvry2Ou o2) {
					return o2.getQtyPct().compareTo(o1.getQtyPct());
				}
			});
			BigDecimal totalTrgtQty = itemExcel.getTrgtQty();
			BigDecimal totalFreeQty = itemExcel.getFreeQty();
			BigDecimal remainingTrgtQty = totalTrgtQty;
			BigDecimal remainingFreeQty = totalFreeQty;
			BigDecimal assignedTrgtQty = BigDecimal.ZERO;
			BigDecimal assignedFreeQty = BigDecimal.ZERO;
			for (PrcmtDlvry2Ou prcmtDlvry2Ou : sortedOus) {
				BigDecimal qtyPct = prcmtDlvry2Ou.getQtyPct();
				
				BigDecimal qtyDelivd = BigDecimalUtils.basePercentOfRatePct(qtyPct, itemExcel.getTrgtQty()).round(MathContext.DECIMAL64);
				qtyDelivd = qtyDelivd.min(remainingTrgtQty);// At most the remaining quantity.
				remainingTrgtQty = BigDecimalUtils.subs(remainingTrgtQty, qtyDelivd);
				assignedTrgtQty = assignedTrgtQty.add(qtyDelivd);
				if(assignedTrgtQty.add(remainingTrgtQty).compareTo(totalTrgtQty)!=0) throw new IllegalStateException("Error during qty distribution. Assigned target qty + remaining target qty is not equal to the total target qty ");
				
				BigDecimal freeQty = BigDecimalUtils.basePercentOfRatePct(qtyPct, itemExcel.getFreeQty()).round(MathContext.DECIMAL64);
				freeQty = freeQty.min(remainingFreeQty);// At most the remaining quantity.
				remainingFreeQty = BigDecimalUtils.subs(remainingFreeQty, freeQty);
				assignedFreeQty = assignedFreeQty.add(freeQty);
				if(assignedFreeQty.add(remainingFreeQty).compareTo(totalFreeQty)!=0) throw new IllegalStateException("Error during qty distribution. Assigned free qty + remaining free qty is not equal to the total free qty ");
				
				PrcmtDlvryItem2Ou item2Ou = new PrcmtDlvryItem2Ou();
				item2Ou.setFreeQty(freeQty);
				item2Ou.setQtyDlvrd(qtyDelivd);
				item2Ou.setRcvngOrgUnit(itemExcel.getOrgUnit());
				item2Ou.setValueDt(now);
				item2Ous.add(item2Ou);
			}
			// Verify totals.
			if(remainingTrgtQty.compareTo(BigDecimal.ZERO)!=0) throw new IllegalStateException("Error during qty distribution. Remaining taget qty is not equal to zero.");
			if(remainingFreeQty.compareTo(BigDecimal.ZERO)!=0) throw new IllegalStateException("Error during qty distribution. Remaining free qty is not equal to zero.");
			if(assignedTrgtQty.compareTo(totalTrgtQty)!=0) throw new IllegalStateException("Error during qty distribution. Remaining assigned qty is not equal to total target qty.");
			if(assignedFreeQty.compareTo(totalFreeQty)!=0) throw new IllegalStateException("Error during qty distribution. Remaining free qty is not equal to total free qty.");
		}

		return item2Ous;		
	}
	
	public List<PrcmtDlvryItem2Ou> processOrgUnitsFromExcel(PrcmtDlvryItem itemExcel, String recvngOus, Date now) {
		List<PrcmtDlvryItem2Ou> ouList = new ArrayList<PrcmtDlvryItem2Ou>();
		// Important. Catching null
		if(StringUtils.isBlank(recvngOus)) return ouList;
		
		String[] recvngOuArray = StringUtils.split(recvngOus, ENTRY_SEPARATOR);
		for (String recvngOu : recvngOuArray) {
			PrcmtDlvryItem2Ou ou = new PrcmtDlvryItem2Ou();
			ou.setValueDt(now);
			String ouCode = StringUtils.substringBefore(recvngOu, QTY_START);
			if(StringUtils.isBlank(ouCode)) throw new IllegalStateException("Format for receiving org unit incorect. Must have the format recvngOu(d:qtyDelivered)(f:freeQty),recvngOu(d:qtyDelivered)(f:freeQty) - but we have " + recvngOu + " for PrcmtDlvryItem with artPic: " + itemExcel.getArtPic());
			ou.setRcvngOrgUnit(ouCode);
			String[] qtyStrings = StringUtils.substringsBetween(recvngOu, QTY_START, QTY_END);
			for (String qtyString : qtyStrings) {
				if(StringUtils.isBlank(qtyString)) continue;
				String qtyDeliverdStr = StringUtils.substringAfter(qtyString, DELIVERED_PREFIX);
				if(!StringUtils.isNumeric(qtyDeliverdStr))throw new IllegalStateException("Format for receiving org unit incorect. qtyDelivered is not numeric: "+qtyDeliverdStr+" Must have the format recvngOu(d:qtyDelivered)(f:freeQty),recvngOu(d:qtyDelivered)(f:freeQty) - but we have " + recvngOu + " for PrcmtDlvryItem with artPic: " + itemExcel.getArtPic());
				ou.setQtyDlvrd(new BigDecimal(qtyDeliverdStr));
				String freeQtyStr = StringUtils.substringAfter(qtyString, FREE_PREFIX);
				if(!StringUtils.isNumeric(freeQtyStr))throw new IllegalStateException("Format for receiving org unit incorect. freeQtyStr is not numeric: "+freeQtyStr+" Must have the format recvngOu(d:qtyDelivered)(f:freeQty),recvngOu(d:qtyDelivered)(f:freeQty) - but we have " + recvngOu + " for PrcmtDlvryItem with artPic: " + itemExcel.getArtPic());
				ou.setFreeQty(new BigDecimal(freeQtyStr));
			}
			ouList.add(ou);
		}
		return ouList ;
	}

	public List<PrcmtDlvryItem2StrgSctn> processSectionFromExcel(PrcmtDlvryItem itemExcel, String strgSctns, Date now){
		// first check if storage sections are defined
		List<PrcmtDlvryItem2StrgSctn> item2SectionList = new ArrayList<PrcmtDlvryItem2StrgSctn>();
		
		// Important. Catching null
		if(StringUtils.isBlank(strgSctns)) return item2SectionList;
			
		String[] strgSctnArray = StringUtils.split(strgSctns, ",");
		for (String strgSctn : strgSctnArray) {
			String qtyStoredStr = StringUtils.substringBetween(strgSctn, "(", ")");
			if(StringUtils.isBlank(qtyStoredStr) || StringUtils.isNumeric(qtyStoredStr))
				throw new IllegalStateException("Qty stored must have the format sectionCode(qtyStored) - but we have " + strgSctn + " for PrcmtDlvryItem with artPic: " + itemExcel.getArtPic());
			String sectionCode = StringUtils.substringBefore(strgSctn, "(");
			if(StringUtils.isBlank(sectionCode))
				throw new IllegalStateException("Qty stored must have the format sectionCode(qtyStored) - but we have " + strgSctn + " for PrcmtDlvryItem with artPic: " + itemExcel.getArtPic());
					
			PrcmtDlvryItem2StrgSctn item2Section = createPrcmtDlvryItem2StrgSctn(itemExcel, now, sectionCode, new BigDecimal(qtyStoredStr));
			item2SectionList.add(item2Section);
		}
		
		return item2SectionList;
	}
	
	private PrcmtDlvryItem2StrgSctn createPrcmtDlvryItem2StrgSctn(PrcmtDlvryItem itemExcel, Date now, String section, BigDecimal qtyStored){
		PrcmtDlvryItem2StrgSctn item2Section = new PrcmtDlvryItem2StrgSctn();
		item2Section.setValueDt(now);
		item2Section.setCntnrIdentif(itemExcel.getCntnrIdentif());
		item2Section.setQtyStrd(qtyStored);
		item2Section.setStrgSection(section);
		
		// find 
		Long lotInSectionCount = articleLot2StrgSctnLookup.countByArtPicAndSection(itemExcel.getArtPic(), section);
		BigDecimal stkQtyPreDlvry = BigDecimal.ZERO;
		if(lotInSectionCount>0){
			List<StkArticleLot2StrgSctn> arts = articleLot2StrgSctnLookup.findByArtPicAndSection(itemExcel.getArtPic(), section, 0, lotInSectionCount.intValue());
			for (StkArticleLot2StrgSctn articleLot2StrgSctn : arts) {
				StkLotInSctnStockQty stockQty = lotInSctnStockQtyLookup.findLatest(articleLot2StrgSctn.getArtPic(), articleLot2StrgSctn.getLotPic(), articleLot2StrgSctn.getSection());
				stkQtyPreDlvry = BigDecimalUtils.sum(stkQtyPreDlvry, stockQty.getStockQty());
			}
		}
		item2Section.setStkQtyPreDlvry(stkQtyPreDlvry);
		
		return item2Section;
	}
	

	static enum Selections {
		ArtPic, ArtPicAndSupplierPic, ArtPicAndManufacturerPic, ArtPicAndSupplierPicAndManufacturerPic
	}
	static class Selector{
		Selections selections;
		Long count;
	}

	public PrcmtDlvryItem findMatchingItemFromDelivery(PrcmtDelivery delivery, PrcmtDlvryItem item,
			List<PrcmtDlvryItem2StrgSctn> item2SectionList, List<PrcmtDlvryItem2Ou> item2Ous) {

		Long itemInDlvryCount = itemLookup.countByCntnrIdentifAndArtPic(item.getCntnrIdentif(), item.getArtPic());
		if(itemInDlvryCount<=0) return null;
		

		// Find an article to merge to
		StkArtLotMgmnt lm = artLotMgmntLookup.findCurrentByArtPic(item.getArtPic());
		if(lm==null)lm=new StkArtLotMgmnt();
		int processed = 0;
		int max = 100;
		while(processed<itemInDlvryCount){
			int start = processed;
			processed+=max;
			List<PrcmtDlvryItem> found = itemLookup.findByCntnrIdentifAndArtPic(item.getCntnrIdentif(), item.getArtPic(), start, max);
			// If we find one with the needed caracteristics, we use it.
			for (PrcmtDlvryItem existingItem : found) {
				// Lot must have same expiration date
				if(Boolean.TRUE.equals(lm.getLotAssgnmtExpirDt()) && !CalendarUtil.isSameDay(item.getExpirDt(), existingItem.getExpirDt())) continue;
				// Lot must have same supplier pic
				if(Boolean.TRUE.equals(lm.getLotAssgnmtSupplrPic()) && !StringUtils.equalsIgnoreCase(item.getSupplierPic(), existingItem.getSupplierPic())) continue;
				// Lot mus have same manufacturer pic
				if(Boolean.TRUE.equals(lm.getLotAssgnmtManufPic()) && !StringUtils.equalsIgnoreCase(item.getManufacturerPic(), existingItem.getManufacturerPic())) continue;
				return existingItem;
			}
		}
		return null;
	}
	
	public List<StkArticleLot> selectArticleLotFromPeerArtPic(PrcmtDlvryItem item, int maxResult){

		final String artPic = item.getArtPic();
		StkArtLotMgmnt current = artLotMgmntLookup.findCurrentByArtPic(artPic);
		if(current==null)current=new StkArtLotMgmnt();
		
		if(Boolean.TRUE.equals(current.getLotAssgnmtManual())) throw new IllegalStateException("Missing Article Lot. Musst be manually assigned."); 
		
		List<StkArticleLot> candidateLots = new ArrayList<StkArticleLot>();
//		Selector selector = new Selector();
		// Do i have a dlvryItem of this delivery with the same artPic?

		// No need to search. Each delivery creates a propert lot.
		if(Boolean.TRUE.equals(current.getLotAssgnmtDlvry())) return candidateLots;
		// There is no article lot with this article code yet
		Long artLotCount = articleLotLookup.countByArtPic(artPic);
		if(artLotCount<=0)return candidateLots;
		
		Long expirDtCount = -1l;
		Date expirDt = item.getExpirDt();
		if(Boolean.TRUE.equals(current.getLotAssgnmtExpirDt()) && expirDt!=null){
			// trim expir date to the day and look for candidates.
			expirDt = DateUtils.truncate(expirDt, Calendar.DAY_OF_MONTH);
			expirDtCount = articleLotLookup.countByArtPicAndExpirDt(artPic, expirDt);
			if(expirDtCount<=0)return candidateLots;
		}
		
		Long supplrPicCount = -1l;
		String supplierPic = item.getSupplierPic();
		if(Boolean.TRUE.equals(current.getLotAssgnmtSupplrPic()) && StringUtils.isNotBlank(supplierPic)){
			supplrPicCount = articleLotLookup.countByArtPicAndSupplierPic(artPic, supplierPic);
			if(supplrPicCount<=0)return candidateLots;
		}

		Long manufPicCount = -1L;
		String manufacturerPic = item.getManufacturerPic();
		if(Boolean.TRUE.equals(current.getLotAssgnmtManufPic()) && StringUtils.isNotBlank(manufacturerPic)){
			manufPicCount = articleLotLookup.countByArtPicAndManufacturerPic(artPic, manufacturerPic);
			if(manufPicCount<=0)return candidateLots;
		}
		
		if(expirDtCount>0 && supplrPicCount>0 && manufPicCount>0){
			Long count = articleLotLookup.countByArtPicAndSupplierPicAndManufacturerPicAndExpirDt(artPic, supplierPic, manufacturerPic, expirDt);
			if(count<=0)return candidateLots;
			return articleLotLookup.findByArtPicAndSupplierPicAndManufacturerPicAndExpirDt(artPic, supplierPic, manufacturerPic, expirDt, 0, maxResult);
		}
		if(expirDtCount>0 && supplrPicCount>0){
			Long count = articleLotLookup.countByArtPicAndSupplierPicAndExpirDt(artPic, supplierPic, expirDt);
			if(count<=0)return candidateLots;
			return articleLotLookup.findByArtPicAndSupplierPicAndExpirDt(artPic, supplierPic, expirDt, 0, maxResult);
		}		
		if(expirDtCount>0 && manufPicCount>0){
			Long count = articleLotLookup.countByArtPicAndManufacturerPicAndExpirDt(artPic, manufacturerPic, expirDt);
			if(count<=0)return candidateLots;
			return articleLotLookup.findByArtPicAndManufacturerPicAndExpirDt(artPic, manufacturerPic, expirDt, 0, maxResult);
		}
		if(supplrPicCount>0 && manufPicCount>0){
			Long count = articleLotLookup.countByArtPicAndSupplierPicAndManufacturerPic(artPic, supplierPic, manufacturerPic);
			if(count<=0)return candidateLots;
			return articleLotLookup.findByArtPicAndSupplierPicAndManufacturerPicAndExpirDt(artPic, supplierPic, manufacturerPic, expirDt, 0, maxResult);
		}
		if(expirDtCount>0){
			Long count = articleLotLookup.countByArtPicAndExpirDt(artPic, expirDt);
			if(count<=0)return candidateLots;
			return articleLotLookup.findByArtPicAndExpirDt(artPic, expirDt, 0, maxResult);
		}		
		if(supplrPicCount>0){
			Long count = articleLotLookup.countByArtPicAndSupplierPic(artPic, supplierPic);
			if(count<=0)return candidateLots;
			return articleLotLookup.findByArtPicAndSupplierPic(artPic, supplierPic, 0, maxResult);
		}		
		if(manufPicCount>0){
			Long count = articleLotLookup.countByArtPicAndManufacturerPic(artPic, manufacturerPic);
			if(count<=0)return candidateLots;
			return articleLotLookup.findByArtPicAndManufacturerPic(artPic, manufacturerPic, 0, maxResult);
		}

		return articleLotLookup.findByArtPic(artPic, 0, maxResult);
	}
	
	
	/*
	 * Try to find the article lot whose section is the closest to the section of this item.
	 */
	public StkArticleLot narrowArticleLotCandidates(PrcmtDlvryItem item, List<PrcmtDlvryItem2StrgSctn> item2SectionList, List<StkArticleLot> candidateLots) {
		for (StkArticleLot articleLot : candidateLots) {
			for (PrcmtDlvryItem2StrgSctn strgSctn : item2SectionList) {
				if(StringUtils.equalsIgnoreCase(articleLot.getSection(),strgSctn.getStrgSection())) return articleLot;
			}
		}
		// if no match, look if the parent of the lot is a parent of the section of the item.
		for (StkArticleLot articleLot : candidateLots) {
			for (PrcmtDlvryItem2StrgSctn strgSctn : item2SectionList) {
				StkSection itemSestionEntity = stkSectionLookup.findByIdentif(strgSctn.getStrgSection());
				StkSection lotSestionEntity = stkSectionLookup.findByIdentif(articleLot.getSection());
				// Take if item is in the lot section.
				if(itemSestionEntity.isChildOf(lotSestionEntity.getIdentif())) return articleLot;
				// take if lot is in the item section.
				if(lotSestionEntity.isChildOf(itemSestionEntity.getIdentif())) return articleLot;
			}
		}
		
		return candidateLots.iterator().next();
	}

	public void storeStorageSections(PrcmtDlvryItem item, List<PrcmtDlvryItem2StrgSctn> item2SectionList){
		// while saving, check if a item of this category went in befor and merge if necessary.
		for (PrcmtDlvryItem2StrgSctn dlvryItem2StrgSctn : item2SectionList) {
			// Always try to discover a peer in database.
			PrcmtDlvryItem2StrgSctn found = dlvryItem2StrgSctnLookup.findByCntnrIdentifAndStrgSection(item.getIdentif(), dlvryItem2StrgSctn.getStrgSection());
			if(found!=null) {
				found.setQtyStrd(BigDecimalUtils.sum(found.getQtyStrd(),dlvryItem2StrgSctn.getQtyStrd()));
				found.setStkQtyPreDlvry(dlvryItem2StrgSctn.getStkQtyPreDlvry());
				dlvryItem2StrgSctnEJB.update(found);
			} else {
				dlvryItem2StrgSctn.setCntnrIdentif(item.getIdentif());
				dlvryItem2StrgSctnEJB.create(dlvryItem2StrgSctn);
			}
		}
	}
	
	public void storeRecievingOUs(PrcmtDlvryItem item, List<PrcmtDlvryItem2Ou> item2Ous){
		for (PrcmtDlvryItem2Ou dlvryItem2Ou : item2Ous) {
			// Always try to discover first.
			PrcmtDlvryItem2Ou found = dlvryItem2OuLookup.findByCntnrIdentifAndRcvngOrgUnit(item.getIdentif(), dlvryItem2Ou.getRcvngOrgUnit());
			if(found!=null){
				found.setQtyDlvrd(BigDecimalUtils.sum(found.getQtyDlvrd(), dlvryItem2Ou.getQtyDlvrd()));
				found.setFreeQty(BigDecimalUtils.sum(found.getFreeQty(), dlvryItem2Ou.getFreeQty()));
				dlvryItem2OuEJB.update(found);
			} else {
				dlvryItem2Ou.setCntnrIdentif(item.getIdentif());
				dlvryItem2OuEJB.create(dlvryItem2Ou);
			}
		}		
	}
	
}