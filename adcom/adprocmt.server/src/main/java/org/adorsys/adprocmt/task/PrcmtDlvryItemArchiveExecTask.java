package org.adorsys.adprocmt.task;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.task.CoreAbstBsnsItemArchiveExecTask;
import org.adorsys.adcore.task.CoreAbstEntityBatch;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryArchive;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryCstr;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstryArchive;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemArchive;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtPrcssgStep;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.adorsys.adprocmt.rest.PrcmtBatch;
import org.adorsys.adprocmt.rest.PrcmtDeliveryInjector;
import org.adorsys.adprocmt.rest.PrcmtDlvryItemArchiveEJB;

@Stateless
public class PrcmtDlvryItemArchiveExecTask extends CoreAbstBsnsItemArchiveExecTask<PrcmtDelivery, PrcmtDeliveryArchive, PrcmtDlvryItem, PrcmtDlvryItemArchive, PrcmtDeliveryHstry, PrcmtDeliveryHstryArchive, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr, PrcmtPrcssgStep> {

	@Inject 
	private PrcmtDeliveryInjector injector;
	@Inject
	private PrcmtDlvryItemArchiveEJB itemArchiveEJB;
	@EJB
	private PrcmtBatch batch;
	@EJB
	private PrcmtDlvryItemArchiveExecTask taskEjb;
	
	@Override
	protected CoreAbstBsnsObjInjector<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr> getInjector() {
		return injector;
	}

	@Override
	protected PrcmtDlvryItemArchive newArchiveEntity() {
		return new PrcmtDlvryItemArchive();
	}

	@Override
	protected CoreAbstIdentifiedEJB<PrcmtDlvryItemArchive> getItemArchiveEjb() {
		return itemArchiveEJB;
	}

	@Override
	protected CoreAbstEntityBatch<PrcmtJob, PrcmtStep, PrcmtPrcssgStep> getBatch() {
		return batch;
	}

	@Override
	protected CoreAbstEntityJobExecutor<PrcmtJob, PrcmtStep, PrcmtPrcssgStep> getEjb() {
		return taskEjb;
	}
}
