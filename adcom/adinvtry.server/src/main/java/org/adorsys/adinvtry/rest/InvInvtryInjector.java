package org.adorsys.adinvtry.rest;

import java.security.Principal;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;

import org.adorsys.adcore.auth.AdcomUser;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adcore.rest.CoreAbstBsnsObjBatch;
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
import org.adorsys.adinvtry.jpa.InvInvtryJob;
import org.adorsys.adinvtry.jpa.InvInvtryStep;

public class InvInvtryInjector extends
		CoreAbstBsnsObjInjector<InvInvtry, InvInvtryItem, InvInvtryHstry, InvInvtryJob, InvInvtryStep, InvInvtryCstr> {

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
	private InvInvtryStepLookup stepLookup;
	@EJB
	private InvInvtryStepEJB stepEjb;
	@EJB
	private InvInvtryJobLookup jobLookup;
	@EJB
	private InvInvtryJobEJB jobEjb;
	@EJB
	private InvInvtryHstryEJB hstrEjb;
	@EJB
	private InvInvtryHstryLookup hstrLookup;
	@EJB
	private InvInvtryItemEJB itemEjb;
	@EJB
	private InvInvtryItemLookup itemLookup;

	@Override
	protected CoreAbstBsnsObjectLookup<InvInvtry> getBsnsObjLookup() {
		return bsnsObjLookup;
	}

	@Override
	protected CoreAbstBsnsObjectEJB<InvInvtry, InvInvtryItem, InvInvtryHstry, InvInvtryJob, InvInvtryStep, InvInvtryCstr> getBsnsObjEjb() {
		return bsnsObjEjb;
	}

	@Override
	protected CoreAbstBsnsItemLookup<InvInvtryItem> getItemLookup() {
		return itemLookup;
	}

	@Override
	protected CoreAbstBsnsItemEJB<InvInvtry, InvInvtryItem, InvInvtryHstry, InvInvtryJob, InvInvtryStep, InvInvtryCstr> getItemEjb() {
		return itemEjb;
	}

	@Override
	protected CoreAbstBsnsObjectHstryLookup<InvInvtryHstry> getHstrLookup() {
		return hstrLookup;
	}

	@Override
	protected CoreAbstBsnsObjectHstryEJB<InvInvtry, InvInvtryItem, InvInvtryHstry, InvInvtryJob, InvInvtryStep, InvInvtryCstr> getHstrEjb() {
		return hstrEjb;
	}

	@Override
	protected String getSequenceGeneratorPrefix() {
		return SequenceGenerator.INVENTORY_SEQUENCE_PREFIXE;
	}

	@Override
	protected CoreAbstEntityJobEJB<InvInvtryJob> getJobEjb() {
		return jobEjb;
	}

	@Override
	protected CoreAbstEntityJobLookup<InvInvtryJob> getJobLookup() {
		return jobLookup;
	}

	@Override
	protected CoreAbstEntityStepEJB<InvInvtryStep> getStepEjb() {
		return stepEjb;
	}

	@Override
	protected CoreAbstEntityStepLookup<InvInvtryStep> getStepLookup() {
		return stepLookup;
	}

	@Override
	protected CoreAbstEntityCstrEJB<InvInvtryCstr> getCstrEjb() {
		return cstrEjb;
	}

	@Override
	protected CoreAbstEntityCstrLookup<InvInvtryCstr> getCstrLookup() {
		return cstrLookup;
	}

	@Override
	protected CoreAbstBsnsObjBatch<InvInvtry, InvInvtryItem, InvInvtryHstry, InvInvtryJob, InvInvtryStep, InvInvtryCstr> getBatch() {
		return batch;
	}

}
