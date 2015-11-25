package org.adorsys.adprocmt.rest;

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
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderCstr;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderHstry;
import org.adorsys.adprocmt.jpa.PrcmtStep;

@Stateless
public class PrcmtProcOrderInjector extends
CoreAbstBsnsObjInjector<PrcmtProcOrder, PrcmtPOItem, PrcmtProcOrderHstry, PrcmtJob, PrcmtStep, PrcmtProcOrderCstr> {

	@EJB
	private PrcmtProcOrderLookup bsnsObjLookup;
	@EJB
	private PrcmtProcOrderEJB bsnsObjEjb;
	@EJB
	private PrcmtBatch batch;
	@EJB
	private PrcmtProcOrderCstrLookup cstrLookup;
	@EJB
	private PrcmtProcOrderCstrEJB cstrEjb;
	@EJB
	private PrcmtStepLookup stepLookup;
	@EJB
	private PrcmtStepEJB stepEjb;
	@EJB
	private PrcmtJobLookup jobLookup;
	@EJB
	private PrcmtJobEJB jobEjb;
	@EJB
	private PrcmtProcOrderHstryEJB hstrEjb;
	@EJB
	private PrcmtProcOrderHstryLookup hstrLookup;
	@EJB
	private PrcmtPOItemEJB itemEjb;
	@EJB
	private PrcmtPOItemLookup itemLookup;

	@Override
	public CoreAbstBsnsObjectLookup<PrcmtProcOrder> getBsnsObjLookup() {
		return bsnsObjLookup;
	}

	@Override
	public CoreAbstBsnsObjectEJB<PrcmtProcOrder, PrcmtPOItem, PrcmtProcOrderHstry, PrcmtJob, PrcmtStep, PrcmtProcOrderCstr> getBsnsObjEjb() {
		return bsnsObjEjb;
	}

	@Override
	public CoreAbstBsnsItemLookup<PrcmtPOItem> getItemLookup() {
		return itemLookup;
	}

	@Override
	public CoreAbstBsnsItemEJB<PrcmtProcOrder, PrcmtPOItem, PrcmtProcOrderHstry, PrcmtJob, PrcmtStep, PrcmtProcOrderCstr> getItemEjb() {
		return itemEjb;
	}

	@Override
	public CoreAbstBsnsObjectHstryLookup<PrcmtProcOrderHstry> getHstrLookup() {
		return hstrLookup;
	}

	@Override
	public CoreAbstBsnsObjectHstryEJB<PrcmtProcOrder, PrcmtPOItem, PrcmtProcOrderHstry, PrcmtJob, PrcmtStep, PrcmtProcOrderCstr> getHstrEjb() {
		return hstrEjb;
	}

	@Override
	public String getSequenceGeneratorPrefix() {
		return SequenceGenerator.INVENTORY_SEQUENCE_PREFIXE;
	}

	@Override
	public CoreAbstEntityJobEJB<PrcmtJob> getJobEjb() {
		return jobEjb;
	}

	@Override
	public CoreAbstEntityJobLookup<PrcmtJob> getJobLookup() {
		return jobLookup;
	}

	@Override
	public CoreAbstEntityStepEJB<PrcmtStep> getStepEjb() {
		return stepEjb;
	}

	@Override
	public CoreAbstEntityStepLookup<PrcmtStep> getStepLookup() {
		return stepLookup;
	}

	@Override
	public CoreAbstEntityCstrEJB<PrcmtProcOrderCstr> getCstrEjb() {
		return cstrEjb;
	}

	@Override
	public CoreAbstEntityCstrLookup<PrcmtProcOrderCstr> getCstrLookup() {
		return cstrLookup;
	}

}
