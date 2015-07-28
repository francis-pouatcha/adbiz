package org.adorsys.adprocmt.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;

import org.adorsys.adcore.utils.CalendarUtil;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adprocmt.jpa.PrcmtDlvryArtPrcssng;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.rest.PrcmtDlvryArtPrcssngEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryItemEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryItemLookup;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

@Stateless
public class PrcmtDlvryArtProcessor {

	@Inject
	private PrcmtDlvryArtPrcssngEJB artPrcssngEJB;
	@Inject
	private PrcmtDlvryItemEJB dlvryItemEJB;
	@Inject
	private PrcmtDlvryItemLookup dlvryItemLookup; 

	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void process(String artPrcssngId){
		String myId = UUID.randomUUID().toString();
		PrcmtDlvryArtPrcssng artPrcssng = artPrcssngEJB.findById(artPrcssngId);
		if(artPrcssng==null) return;
		
		Date now = new Date();
		
		if(artPrcssng.getPrcssngAgent()!=null){
			Date endTme = artPrcssng.getPrcssngEndTme();
			if(endTme!=null && endTme.after(now)) return;
		}
		artPrcssng.setPrcssngAgent(myId);
		artPrcssng.setPrcssngEndTme(DateUtils.addMinutes(now, 10));
		try {
			if(!artPrcssngEJB.lock(artPrcssng)) return;
		} catch(OptimisticLockException ex){
			// noop, somebody else had the lock.
			return;
		}
		
		// Now process the article.
		// Francis: 23.06.2015. We use a constant value of 1000 here because we know the it is theoretically impossible
		// to have 1000 position of the same item in the same delivery.
		List<PrcmtDlvryItem> dlvryItems = dlvryItemLookup.findByBsnsObjNbrAndArtPic(artPrcssng.getDlvryNbr(), artPrcssng.getArtPic(), 0, 1000); // TODO clean cursor
		// We start by sorting per expiration date
		Map<String, List<PrcmtDlvryItem>> byExpirDate = sortByExpirDate(dlvryItems);

		// We sort them per purchase price per unit pre tax
		Map<String, Map<String, List<PrcmtDlvryItem>>> byExpirAndPppu = sortByPppu(byExpirDate);
		// Now each list will have the same lot identification code.
		flattenLotPics(byExpirAndPppu);
				
		PrcmtDlvryArtPrcssng found = artPrcssngEJB.findById(artPrcssngId);
		if(found!=null && StringUtils.equals(found.getPrcssngAgent(), myId)){
			artPrcssngEJB.deleteById(found.getId());
		}  else {
			throw new IllegalStateException("Current thread not owner.");
		}
		// Save items.
		for (PrcmtDlvryItem dlvryItem : dlvryItems) {
			dlvryItemEJB.update(dlvryItem);
		}
		
	}

	private void flattenLotPics(
			Map<String, Map<String, List<PrcmtDlvryItem>>> byExpirAndPppu) {
		Collection<Map<String, List<PrcmtDlvryItem>>> byPppus = byExpirAndPppu.values();
		for (Map<String, List<PrcmtDlvryItem>> byPppu : byPppus) {
			Collection<List<PrcmtDlvryItem>> values = byPppu.values();
			for (List<PrcmtDlvryItem> list : values) {
				String lotPic = SequenceGenerator.getSequence(SequenceGenerator.LOT_SEQUENCE_PREFIXE);
				for (PrcmtDlvryItem di : list) {
					di.setLotPic(lotPic);
				}
			}
		}
		
	}

	private Map<String, Map<String, List<PrcmtDlvryItem>>> sortByPppu(
			Map<String, List<PrcmtDlvryItem>> byExpirDate) {
		Map<String, Map<String, List<PrcmtDlvryItem>>> result = new HashMap<String, Map<String,List<PrcmtDlvryItem>>>();
		Set<String> keySet = byExpirDate.keySet();
		for (String expirDayStr : keySet) {
			Map<String, List<PrcmtDlvryItem>> map = new HashMap<String, List<PrcmtDlvryItem>>();
			result.put(expirDayStr, map);
			List<PrcmtDlvryItem> list = byExpirDate.get(expirDayStr);
			for (PrcmtDlvryItem prcmtDlvryItem : list) {
				String pppuStr = null;
				BigDecimal pppuPreTax = prcmtDlvryItem.getPrchUnitPrcPreTax();
				if(pppuPreTax==null){
					// Fist try to find the pppu
					pppuStr = BigDecimal.ZERO.toString();
				} else {
					pppuStr = pppuPreTax.toString();
				}
				putToMap(pppuStr, prcmtDlvryItem, map);
			}
		}
		return result ;
	}

	public static final String DEFAULT_DATE = "DEFAULT_DATE";
	private Map<String, List<PrcmtDlvryItem>> sortByExpirDate(
			List<PrcmtDlvryItem> dlvryItems) {
		Map<String, List<PrcmtDlvryItem>> result = new HashMap<String, List<PrcmtDlvryItem>>();
		for (PrcmtDlvryItem prcmtDlvryItem : dlvryItems) {
			if(prcmtDlvryItem.getExpirDt()==null){
				putToMap(DEFAULT_DATE,prcmtDlvryItem, result);
			} else {
				String dayStr = CalendarUtil.getDayStr(prcmtDlvryItem.getExpirDt(), null);
				putToMap(dayStr,prcmtDlvryItem, result);
			}
		}
		return result;
	}
	private void putToMap(String key, PrcmtDlvryItem prcmtDlvryItem,
			final Map<String, List<PrcmtDlvryItem>> result) {
		List<PrcmtDlvryItem> list = result.get(key);
		if(list==null) {
			list = new ArrayList<PrcmtDlvryItem>();
			result.put(key, list);
		}
		list.add(prcmtDlvryItem);
	}
}
