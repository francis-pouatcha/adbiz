package org.adorsys.adprocmt.loader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.ejb.Singleton;
import javax.inject.Inject;

import org.adorsys.adprocmt.api.PrcmtDeliveryHolder;
import org.adorsys.adprocmt.api.PrcmtDeliveryItemHolder;
import org.adorsys.adprocmt.api.PrcmtDeliveryManager;
import org.adorsys.adprocmt.api.PrcmtDlvryItem2RcvngOrgUnitHolder;
import org.adorsys.adprocmt.api.PrcmtDlvryItem2StrgSctnHolder;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctn;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
import org.apache.commons.lang3.StringUtils;

@Singleton
public class ProcmtDlvryManagerClient {

	@Inject
	private PrcmtDeliveryManager deliveryManager;

	PrcmtDeliveryHolder deliveryHolder = new PrcmtDeliveryHolder();
	
	public void saveDelivery(PrcmtDeliveryExcel deliveryExcel){
		PrcmtDelivery delivery = new PrcmtDelivery();
		deliveryExcel.copyTo(delivery);
		// New Holder
		this.deliveryHolder = new PrcmtDeliveryHolder();
		this.deliveryHolder.setDelivery(delivery);
		// PRocess org units.
	}
	
	public void saveDlvryItem(PrcmtDlvryItemExcel dlvryItemExcel){
		PrcmtDlvryItem dlvryItem = new PrcmtDlvryItem();
		dlvryItemExcel.copyTo(dlvryItem);
		PrcmtDeliveryItemHolder dlvryItemHolder = new PrcmtDeliveryItemHolder();
		dlvryItemHolder.setDlvryItem(dlvryItem);

		// parse the section provided by the excel file.
		String strgSctns = dlvryItemExcel.getStrgSctns();
		List<PrcmtDlvryItem2StrgSctnHolder> parseStrgSctns = parseStrgSctns(strgSctns);
		// Lookup the same section for an article in the storage.
		if(parseStrgSctns.isEmpty()) parseStrgSctns=lookupSection(dlvryItem);
		// Take a random storage.
		if(parseStrgSctns.isEmpty()) parseStrgSctns=randomStrgSctns(dlvryItem);
		
		dlvryItemHolder.setStrgSctns(parseStrgSctns);
		
		String recvngOus = dlvryItemExcel.getRecvngOus();
		List<PrcmtDlvryItem2RcvngOrgUnitHolder> parseRecvngOus = parseRecvngOus(recvngOus);
		dlvryItemHolder.setRecvngOus(parseRecvngOus);

		deliveryHolder.getDeliveryItems().add(dlvryItemHolder);
		
		if(deliveryHolder.getDeliveryItems().size()>=20){
			PrcmtDeliveryHolder updateDelivery = deliveryManager.updateDelivery(deliveryHolder);
			deliveryHolder = new PrcmtDeliveryHolder();
			deliveryHolder.setDelivery(updateDelivery.getDelivery());
		}
	}

	public void done() {
		deliveryManager.closeDelivery(deliveryHolder);
		deliveryHolder = new PrcmtDeliveryHolder();
	}
	
	public PrcmtDeliveryHolder update(){
		return deliveryManager.updateDelivery(deliveryHolder);
	}
		
	private List<PrcmtDlvryItem2StrgSctnHolder> parseStrgSctns(String strgSctns){
		if(StringUtils.isBlank(strgSctns)) return Collections.emptyList();
		List<PrcmtDlvryItem2StrgSctnHolder> result = new ArrayList<PrcmtDlvryItem2StrgSctnHolder>();
		String[] strings = StringUtils.split(strgSctns, ";");
		for (String strgSctn : strings) {
			if(StringUtils.isBlank(strgSctn)) continue;
			String[] split = StringUtils.split(strgSctn, ":");
			if(!StringUtils.isNumeric(split[1])) continue;
			if(split.length<2) continue;
			PrcmtDlvryItem2StrgSctnHolder holder = new PrcmtDlvryItem2StrgSctnHolder();
			PrcmtDlvryItem2StrgSctn item2StrgSctn = new PrcmtDlvryItem2StrgSctn();
			item2StrgSctn.setStrgSection(split[0]);
			item2StrgSctn.setQtyStrd(new BigDecimal(split[1]));
			holder.setStrgSctn(item2StrgSctn);
			result.add(holder);
		}
		return result;
	}
	
	@Inject
	private StkArticleLot2StrgSctnLookup articleLot2StrgSctnLookup;
	
	private List<PrcmtDlvryItem2StrgSctnHolder> lookupSection(PrcmtDlvryItem dlvryItem){
		List<StkArticleLot2StrgSctn> sections = articleLot2StrgSctnLookup.findByArtPic(dlvryItem.getArtPic(), 0, 1);
		if(sections.isEmpty()) return Collections.emptyList(); 

		StkArticleLot2StrgSctn strgSctn = sections.iterator().next();
		List<PrcmtDlvryItem2StrgSctnHolder> result = new ArrayList<PrcmtDlvryItem2StrgSctnHolder>();
		PrcmtDlvryItem2StrgSctnHolder holder = new PrcmtDlvryItem2StrgSctnHolder();
		PrcmtDlvryItem2StrgSctn item2StrgSctn = new PrcmtDlvryItem2StrgSctn();
		item2StrgSctn.setStrgSection(strgSctn.getStrgSection());
		item2StrgSctn.setQtyStrd(dlvryItem.getQtyDlvrd());
		holder.setStrgSctn(item2StrgSctn);
		result.add(holder);
		return result;
	}
	
	private List<PrcmtDlvryItem2StrgSctnHolder> randomStrgSctns(PrcmtDlvryItem dlvryItem){
		List<PrcmtDlvryItem2StrgSctnHolder> result = new ArrayList<PrcmtDlvryItem2StrgSctnHolder>();
		PrcmtDlvryItem2StrgSctnHolder holder = new PrcmtDlvryItem2StrgSctnHolder();
		PrcmtDlvryItem2StrgSctn item2StrgSctn = new PrcmtDlvryItem2StrgSctn();
		item2StrgSctn.setStrgSection(randomSectionNbr());
		item2StrgSctn.setQtyStrd(dlvryItem.getQtyDlvrd());
		holder.setStrgSctn(item2StrgSctn);
		result.add(holder);
		return result;
	}
	
	Random rdn = new Random();
	private String randomSectionNbr(){
		return "RAY-"+ StringUtils.leftPad((""+(rdn.nextInt(150)+1)), 4, '0') ;
	}

	private List<PrcmtDlvryItem2RcvngOrgUnitHolder> parseRecvngOus(String recvngOus){
		if(StringUtils.isBlank(recvngOus)) return Collections.emptyList();
		List<PrcmtDlvryItem2RcvngOrgUnitHolder> result = new ArrayList<PrcmtDlvryItem2RcvngOrgUnitHolder>();
		String[] strings = StringUtils.split(recvngOus, ";");
		for (String recvngOu : strings) {
			if(StringUtils.isBlank(recvngOu)) continue;
			String[] split = StringUtils.split(recvngOu, ":");
			if(!StringUtils.isNumeric(split[1])) continue;
			if(split.length<2) continue;
			PrcmtDlvryItem2RcvngOrgUnitHolder holder = new PrcmtDlvryItem2RcvngOrgUnitHolder();
			PrcmtDlvryItem2Ou dlvryItem2Ou = new PrcmtDlvryItem2Ou();
			dlvryItem2Ou.setRcvngOrgUnit(split[0]);
			dlvryItem2Ou.setQtyDlvrd(new BigDecimal(split[1]));
			if(split.length>2 && StringUtils.isNumeric(split[2]))
				dlvryItem2Ou.setFreeQty(new BigDecimal(split[2]));
			holder.setRcvngOrgUnit(dlvryItem2Ou);
			result.add(holder);
		}
		return result;
	}
}
