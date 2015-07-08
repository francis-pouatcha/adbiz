package org.adorsys.adinvtry.api;

import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryStatus;
import org.adorsys.adinvtry.rest.InvInvtryEJB;
import org.adorsys.adinvtry.rest.InvInvtryItemEJB;
import org.adorsys.adinvtry.rest.InvInvtryItemLookup;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class InvInvtryMerger {

	@Inject
	private InvInvtryEJB inventoryEJB;
	
	@Inject
	private InvInvtryItemEJB invInvtryItemEJB; 
	@Inject
	private InvInvtryItemLookup invInvtryItemLookup;

	public void setMerged(String invtryNbr) {
		InvInvtry invtry = inventoryEJB.findByIdentif(invtryNbr);
		if(invtry==null) return;
		if(invtry.getMergedDate()!=null) {
			inventoryEJB.deleteById(invtry.getId());
		}
		invtry.setInvtryStatus(InvInvtryStatus.MERGED);
		invtry.setMergedDate(new Date());
		inventoryEJB.update(invtry);
	}

	public void setMerging(String invtryNbr) {
		InvInvtry invtry = inventoryEJB.findByIdentif(invtryNbr);
		if(invtry==null) return;
		invtry.setInvtryStatus(InvInvtryStatus.MERGED);
		inventoryEJB.update(invtry);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void mergeTo(String itemIdentif, String containerId) {
		if(StringUtils.equals(itemIdentif, containerId)) return;
		InvInvtryItem oldItem = invInvtryItemLookup.findByIdentif(itemIdentif);
		if(oldItem==null) return;
		InvInvtryItem invInvtryItem = new InvInvtryItem();
		oldItem.copyTo(invInvtryItem);
		invInvtryItem.setBsnsObjNbr(containerId);
		String newIdentif = InvInvtryItem.toIdentifier(invInvtryItem.getBsnsObjNbr(), invInvtryItem.getAcsngUser(), 
				invInvtryItem.getLotPic(), invInvtryItem.getArtPic(), invInvtryItem.getSection());
		InvInvtryItem found = invInvtryItemLookup.findByIdentif(newIdentif);
		// Replace existing if not existent, or not accessed or accessed before current.
		if(found!=null){
			if(found.getAcsngDt()==null || (oldItem.getAcsngDt()!=null && oldItem.getAcsngDt().after(found.getAcsngDt()))){
				// update found
				oldItem.copyTo(found);
				invInvtryItemEJB.update(found);
			}
		} else {
			invInvtryItemEJB.create(invInvtryItem);
		}
		invInvtryItemEJB.deleteById(oldItem.getId());
	}

}
