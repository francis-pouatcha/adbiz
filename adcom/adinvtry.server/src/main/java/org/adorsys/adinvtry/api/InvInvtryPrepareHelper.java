package org.adorsys.adinvtry.api;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.auth.AdSystem;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.rest.InvInvtryItemEJB;
import org.adorsys.adinvtry.rest.InvInvtryItemLookup;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.rest.StkArticleLotLookup;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class InvInvtryPrepareHelper {

	@Inject
	private InvInvtryItemEJB invInvtryItemEJB; 

	@Inject
	private StkArticleLotLookup articleLotLookup;
	@Inject
	private InvInvtryItemLookup invInvtryItemLookup;

	@Inject
	private SecurityUtil securityUtil;
		
	@AdSystem
	public boolean createInvntryItems(List<StkArticleLot2StrgSctn> lot2Sections, InvInvtry inventory){
		boolean modified = false;
		String loginName = inventory.getAcsngUser();
		if(StringUtils.isBlank(loginName))
			loginName = securityUtil.getCurrentLoginName();
		for (StkArticleLot2StrgSctn lot2StrgSctn : lot2Sections) {
			String identifier = InvInvtryItem.toIdentifier(inventory.getInvtryNbr(), loginName, lot2StrgSctn.getLotPic(), 
					lot2StrgSctn.getArtPic(), lot2StrgSctn.getStrgSection());
			InvInvtryItem invtryItem = invInvtryItemLookup.findByIdentif(identifier);
			if(invtryItem!=null) continue;

			invtryItem = new InvInvtryItem();
			
			StkArticleLot articleLot = articleLotLookup.findByIdentif(invtryItem.getLotPic());
			articleLot.contentEquals(invtryItem);

			invtryItem.setAcsngUser(loginName);
			invtryItem.setBsnsObjNbr(inventory.getInvtryNbr());
			invtryItem.setLotPic(lot2StrgSctn.getLotPic());
			invtryItem.setArtPic(lot2StrgSctn.getArtPic());
			invtryItem.setSection(lot2StrgSctn.getStrgSection());
			invtryItem.setArtName(lot2StrgSctn.getArtName());
			
			try {
				invtryItem = invInvtryItemEJB.create(invtryItem);
				modified = true;
			} catch (EntityExistsException e){
				// Noop
			}
		}
		return modified;
	}
}
