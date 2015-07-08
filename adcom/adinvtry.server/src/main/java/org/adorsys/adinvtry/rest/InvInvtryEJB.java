package org.adorsys.adinvtry.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adcore.rest.CoreAbstEntityHstryEJB;
import org.adorsys.adcore.rest.CoreAbstEntityHstryLookup;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adinvtry.api.InventoryInfo;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryType;
import org.adorsys.adinvtry.repo.InvInvtryRepository;

@Stateless
public class InvInvtryEJB extends CoreAbstBsnsObjectEJB<InvInvtry, InvInvtryItem, InvInvtryHstry>
{

	@Inject
	private InvInvtryRepository repository;

	@Inject
	private InvInvtryItemEJB itemEJB;
	@Inject
	private InvInvtryItemLookup itemLookup;
	@Inject
	private InvInvtryLookup lookup;
	@Inject
	private InvInvtryHstryLookup hstryLookup;
	@Inject
	private InvInvtryHstryEJB hstryEjb;
	@EJB
	private InvInvtryEJB ejb; 
	@EJB
	private SecurityUtil securityUtil;
	
	public InvInvtry create(InvInvtry entity){
		if(entity.getTxType()==null)entity.setTxType(InvInvtryType.FREE_INV.name());
		return super.create(entity);
	}

	public void handleInconsistentInvtry(@Observes @InvInconsistentInvtryEvent String bsnsObjNbr){
		super.handleInconsistentBsnsObj(bsnsObjNbr);
	}

	public void handleConsistentInvtry(@Observes @InvConsistentInvtryEvent String bsnsObjNbr){
		super.handleConsistentBsnsObj(bsnsObjNbr);
	}

	@Override
	protected CoreAbstBsnsObjectRepo<InvInvtry> getBsnsRepo() {
		return repository;
	}

	@Override
	protected CoreAbstBsnsObjectLookup<InvInvtry> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstBsnsObjectEJB<InvInvtry, InvInvtryItem, InvInvtryHstry> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstBsnsItemLookup<InvInvtryItem> getItemLookup() {
		return itemLookup;
	}

	@Override
	protected CoreAbstBsnsItemEJB<InvInvtryItem> getItemEjb() {
		return itemEJB;
	}

	@Override
	protected CoreAbstEntityHstryLookup<InvInvtryHstry> getHistoryLookup() {
		return hstryLookup;
	}

	@Override
	protected CoreAbstEntityHstryEJB<InvInvtryHstry> getHistoryEjb() {
		return hstryEjb;
	}

	@Override
	protected String getSequenceGeneratorPrefix() {
		return SequenceGenerator.INVENTORY_SEQUENCE_PREFIXE;
	}

	@Override
	protected TermWsUserPrincipal getCallerPrincipal() {
		return securityUtil.getCallerPrincipal();
	}

	@Override
	protected String prinHstryInfo(InvInvtry entity) {
		return InventoryInfo.prinInfo(entity);
	}

}
