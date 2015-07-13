package org.adorsys.adinvtry.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryCstr;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryJob;
import org.adorsys.adinvtry.jpa.InvInvtryStep;
import org.adorsys.adinvtry.repo.InvInvtryItemRepository;

@Stateless
public class InvInvtryItemEJB extends CoreAbstBsnsItemEJB<InvInvtry, InvInvtryItem, InvInvtryHstry, 
	InvInvtryJob, InvInvtryStep, InvInvtryCstr>
{

	@Inject
	private InvInvtryItemRepository repository;
	@Inject
	private InvInvtryInjector injector;

	@Override
	protected CoreAbstBsnsObjInjector<InvInvtry, InvInvtryItem, InvInvtryHstry, InvInvtryJob, InvInvtryStep, InvInvtryCstr> getInjector() {
		return injector;
	}

	@Override
	protected CoreAbstBsnsItemRepo<InvInvtryItem> getBsnsRepo() {
		return repository;
	}

	@Override
	protected Boolean checkSameQty(List<InvInvtryItem> compareList) {
		BigDecimal qty = null;
		Boolean sameQty = Boolean.FALSE;
		int count = 0;
		for (InvInvtryItem item : compareList) {
			if(item.getDisabledDt()!=null) continue;
			if(count==0){
				qty = item.getAsseccedQty();
				if(qty!=null)
					sameQty = Boolean.TRUE;
			} else {
				if(item.getAsseccedQty()==null || !BigDecimalUtils.strictEquals(qty, item.getAsseccedQty())){
					sameQty = Boolean.FALSE;
				} else {
					sameQty = Boolean.TRUE;
				}
			}
			count +=1;
		}
		return sameQty;
	}
}
