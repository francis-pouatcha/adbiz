package org.adorsys.adinvtry.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.repo.InvInvtryItemRepository;

@Stateless
public class InvInvtryItemEJB extends CoreAbstBsnsItemEJB<InvInvtryItem>
{

	@Inject
	private InvInvtryItemRepository repository;

	@Inject
	private InvInvtryItemLookup lookup;
	
	@EJB
	private InvInvtryItemEJB ejb;
	
	@Inject
	@InvConsistentInvtryEvent
	private Event<String> consistentEvent;
	@Inject
	@InvInconsistentInvtryEvent
	private Event<String> inconsistentEvent;

	@Override
	protected CoreAbstBsnsItemRepo<InvInvtryItem> getBsnsRepo() {
		return repository;
	}
	@Override
	protected CoreAbstBsnsItemLookup<InvInvtryItem> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstBsnsItemEJB<InvInvtryItem> getEjb() {
		return ejb;
	}
	@Override
	protected void fireInconsistentEvent(String hldrNbr) {
		inconsistentEvent.fire(hldrNbr);
	}
	@Override
	protected void fireConsistentEvent(String hldrNbr) {
		consistentEvent.fire(hldrNbr);
	}

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
