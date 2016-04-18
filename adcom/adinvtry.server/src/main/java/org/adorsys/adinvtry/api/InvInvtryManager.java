package org.adorsys.adinvtry.api;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.exceptions.AdRestException;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryCstr;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtry_;
import org.adorsys.adinvtry.jpa.InvJob;
import org.adorsys.adinvtry.jpa.InvStep;
import org.adorsys.adinvtry.rest.InvInvtryInjector;
import org.adorsys.adinvtry.rest.InvInvtrySearchInput;
import org.adorsys.adinvtry.rest.InvInvtrySearchResult;
import org.adorsys.adstock.jpa.StkLotInSctnStockQty;
import org.adorsys.adstock.rest.StkLotInSctnStockQtyLookup;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class InvInvtryManager  extends CoreAbstBsnsObjectManager<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr, CoreAbstBsnsObjectSearchInput<InvInvtry>>{

	@Inject
	private InvInvtryInjector injector;

	@Inject
	private StkLotInSctnStockQtyLookup lotInSctnStockQtyLookup;

	@Override
	protected CoreAbstBsnsObjInjector<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr> getInjector() {
		return injector;
	}

	@Override
	protected Field[] getEntityFields() {
		return InvInvtry_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsObjectSearchInput<InvInvtry> newSearchInput() {
		return new InvInvtrySearchInput();
	}

	@Override
	protected CoreAbstBsnsObjectSearchResult<InvInvtry> newSearchResult(
			Long size, List<InvInvtry> resultList,
			CoreAbstBsnsObjectSearchInput<InvInvtry> searchInput) {
		return new InvInvtrySearchResult(size, resultList, searchInput);
	}

	@Override
	public InvInvtry initiateBsnsObj(InvInvtry bsnsObj) {
		bsnsObj = super.initiateBsnsObj(bsnsObj);
		return bsnsObj;
	}
	
	@Override
	public InvInvtryItem updateAsseccedQty(String identif, String itemIdentif, BigDecimal asseccedQty, Date acsngDt, String acsngUser) throws AdRestException {
		loadExistingBsnsObject(identif); 
		InvInvtryItem existing = loadExistingBsnsItem(identif, itemIdentif);
		//do not work yet, get currentLoginName in frontend
		String currentLoginName = callerPrincipal.getLoginName();
//		String currentLoginName = acsngUser;
		
		// In any case, it is important to read the stock quantity at the moment of the accessment.
		// I will even prefer we ignore the accessing date given by the front end. As well as the 
		// user name.
//		if(acsngDt==null) 
		acsngDt = new Date();
		BigDecimal expectedStockQty = readStockQty(existing.getArtPic(), existing.getLotPic(), existing.getSection());
		
		Boolean modifyingInv = true;
		if(existing.getAcsngDt()!=null){
			// We can modify the current item under following conditions
			// 1- existing.getAcsngDt()==null ==> It was a prepared inventory.
			// 2- existing.getAcsngDt()!=null but the currentLoginName has changed
			// 3- existing.getAcsngDt()!=null, the currentLoginName is same but the expectedStockQty has changed 
			if(!StringUtils.equals(existing.getAcsngUser(), currentLoginName)){
				modifyingInv = false;
			} else if(!BigDecimalUtils.strictEquals(existing.getExpectedQty() ,expectedStockQty)){
				modifyingInv = false;
			}
		}
		if(modifyingInv){// The is no accessing yet.
			existing.setAcsngDt(acsngDt);
			existing.setAsseccedQty(asseccedQty);
			existing.setAcsngUser(currentLoginName);
			existing.setExpectedQty(expectedStockQty);
			existing.setQtyBefore(expectedStockQty);
			existing.evlte();
			
			return getInjector().getItemEjb().update(existing);
		} else {
			InvInvtryItem invInvtryItem = new InvInvtryItem();
			existing.copyTo(invInvtryItem);
			invInvtryItem.setAcsngUser(currentLoginName);
			invInvtryItem.setCntnrIdentif(existing.getCntnrIdentif());
			invInvtryItem.setValueDt(new Date());
			invInvtryItem.setAcsngDt(acsngDt);
			invInvtryItem.setAsseccedQty(asseccedQty);
			invInvtryItem.setExpectedQty(expectedStockQty);
			invInvtryItem.setQtyBefore(expectedStockQty);
			invInvtryItem.evlte();
			return getInjector().getItemEjb().create(invInvtryItem);
		}
	}
	
	private BigDecimal readStockQty(String artPic, String lotPic, String section){
		StkLotInSctnStockQty sctnStockQty = lotInSctnStockQtyLookup.findLatest(artPic, lotPic, section);
		// Read the current stock quantity. This is very important.
		// An inventory should always be adaptive. If the expected quatity 
		// of an inventory item changes, we can not modify that item anymore.
		// This method will create a new item.
		return sctnStockQty!=null?sctnStockQty.getStockQty():BigDecimal.ZERO;
	}
	
}
