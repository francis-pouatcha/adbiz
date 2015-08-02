package org.adorsys.adinvtry.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryLookup;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adcore.rest.CoreAbstEntityCstrEJB;
import org.adorsys.adcore.rest.CoreAbstEntityCstrLookup;
import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryCstr;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvJob;
import org.adorsys.adinvtry.jpa.InvStep;

@Stateless
public class InvInvtryInjector extends
		CoreAbstBsnsObjInjector<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr> {

	@EJB
	private InvInvtryLookup bsnsObjLookup;
	@EJB
	private InvInvtryEJB bsnsObjEjb;
	@EJB
	private InvInvtryBatch batch;
	@EJB
	private InvInvtryCstrLookup cstrLookup;
	@EJB
	private InvInvtryCstrEJB cstrEjb;
	@EJB
	private InvStepLookup stepLookup;
	@EJB
	private InvStepEJB stepEjb;
	@EJB
	private InvJobLookup jobLookup;
	@EJB
	private InvJobEJB jobEjb;
	@EJB
	private InvInvtryHstryEJB hstrEjb;
	@EJB
	private InvInvtryHstryLookup hstrLookup;
	@EJB
	private InvInvtryItemEJB itemEjb;
	@EJB
	private InvInvtryItemLookup itemLookup;

	@Override
	public CoreAbstBsnsObjectLookup<InvInvtry> getBsnsObjLookup() {
		return bsnsObjLookup;
	}

	@Override
	public CoreAbstBsnsObjectEJB<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr> getBsnsObjEjb() {
		return bsnsObjEjb;
	}

	@Override
	public CoreAbstBsnsItemLookup<InvInvtryItem> getItemLookup() {
		return itemLookup;
	}

	@Override
	public CoreAbstBsnsItemEJB<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr> getItemEjb() {
		return itemEjb;
	}

	@Override
	public CoreAbstBsnsObjectHstryLookup<InvInvtryHstry> getHstrLookup() {
		return hstrLookup;
	}

	@Override
	public CoreAbstBsnsObjectHstryEJB<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr> getHstrEjb() {
		return hstrEjb;
	}

	@Override
	public String getSequenceGeneratorPrefix() {
		return SequenceGenerator.INVENTORY_SEQUENCE_PREFIXE;
	}

	@Override
	public CoreAbstEntityJobEJB<InvJob> getJobEjb() {
		return jobEjb;
	}

	@Override
	public CoreAbstEntityJobLookup<InvJob> getJobLookup() {
		return jobLookup;
	}

	@Override
	public CoreAbstEntityStepEJB<InvStep> getStepEjb() {
		return stepEjb;
	}

	@Override
	public CoreAbstEntityStepLookup<InvStep> getStepLookup() {
		return stepLookup;
	}

	@Override
	public CoreAbstEntityCstrEJB<InvInvtryCstr> getCstrEjb() {
		return cstrEjb;
	}

	@Override
	public CoreAbstEntityCstrLookup<InvInvtryCstr> getCstrLookup() {
		return cstrLookup;
	}

}
