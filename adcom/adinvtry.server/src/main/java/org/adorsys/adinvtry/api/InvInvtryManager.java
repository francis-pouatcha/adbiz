package org.adorsys.adinvtry.api;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.exceptions.AdRestException;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtry_;
import org.adorsys.adinvtry.jpa.InvInvtryCstr;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvJob;
import org.adorsys.adinvtry.jpa.InvStep;
import org.adorsys.adinvtry.rest.InvInvtryEJB;
import org.adorsys.adinvtry.rest.InvInvtryInjector;
import org.adorsys.adinvtry.rest.InvInvtrySearchInput;
import org.adorsys.adinvtry.rest.InvInvtrySearchResult;
import org.apache.commons.lang3.time.DateUtils;

@Stateless
public class InvInvtryManager  extends CoreAbstBsnsObjectManager<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr, CoreAbstBsnsObjectSearchInput<InvInvtry>>{

	@Inject
	private InvInvtryInjector injector;
	
	@Inject
	private InvInvtryEJB ejb;

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
	
	public void prepareInvtry(String identif){
		ejb.prepareInvtry(identif);
	}

	public InvInvtryItem updateAsseccedQty(String identif, String itemIdentif, BigDecimal asseccedQty, Date acsngDt) throws AdRestException {
		loadExistingBsnsObject(identif); 
		InvInvtryItem existing = loadExistingBsnsItem(identif, itemIdentif);

		String currentLoginName = callerPrincipal.getLoginName();
		if(acsngDt==null) acsngDt = new Date();
		if(existing.getAcsngDt()==null){
			existing.setAcsngDt(acsngDt);
			existing.setAsseccedQty(asseccedQty);
			existing.setAcsngUser(currentLoginName);
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
			invInvtryItem.evlte();
			return getInjector().getItemEjb().create(invInvtryItem);
		}
	}
}
