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
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryCstr;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtStep;

@Stateless
public class PrcmtDeliveryInjector extends
CoreAbstBsnsObjInjector<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr> {

	@EJB
	private PrcmtDeliveryLookup bsnsObjLookup;
	@EJB
	private PrcmtDeliveryEJB bsnsObjEjb;
	@EJB
	private PrcmtBatch batch;
	@EJB
	private PrcmtDeliveryCstrLookup cstrLookup;
	@EJB
	private PrcmtDeliveryCstrEJB cstrEjb;
	@EJB
	private PrcmtStepLookup stepLookup;
	@EJB
	private PrcmtStepEJB stepEjb;
	@EJB
	private PrcmtJobLookup jobLookup;
	@EJB
	private PrcmtJobEJB jobEjb;
	@EJB
	private PrcmtDeliveryHstryEJB hstrEjb;
	@EJB
	private PrcmtDeliveryHstryLookup hstrLookup;
	@EJB
	private PrcmtDlvryItemEJB itemEjb;
	@EJB
	private PrcmtDlvryItemLookup itemLookup;

	@Override
	public CoreAbstBsnsObjectLookup<PrcmtDelivery> getBsnsObjLookup() {
		return bsnsObjLookup;
	}

	@Override
	public CoreAbstBsnsObjectEJB<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr> getBsnsObjEjb() {
		return bsnsObjEjb;
	}

	@Override
	public CoreAbstBsnsItemLookup<PrcmtDlvryItem> getItemLookup() {
		return itemLookup;
	}

	@Override
	public CoreAbstBsnsItemEJB<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr> getItemEjb() {
		return itemEjb;
	}

	@Override
	public CoreAbstBsnsObjectHstryLookup<PrcmtDeliveryHstry> getHstrLookup() {
		return hstrLookup;
	}

	@Override
	public CoreAbstBsnsObjectHstryEJB<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr> getHstrEjb() {
		return hstrEjb;
	}

	@Override
	public String getSequenceGeneratorPrefix() {
		return SequenceGenerator.PRCMT_DELIVERY_SEQUENCE_PREFIXE;
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
	public CoreAbstEntityCstrEJB<PrcmtDeliveryCstr> getCstrEjb() {
		return cstrEjb;
	}

	@Override
	public CoreAbstEntityCstrLookup<PrcmtDeliveryCstr> getCstrLookup() {
		return cstrLookup;
	}

}
