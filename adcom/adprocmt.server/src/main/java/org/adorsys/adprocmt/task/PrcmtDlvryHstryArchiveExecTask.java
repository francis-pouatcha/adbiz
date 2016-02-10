package org.adorsys.adprocmt.task;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.rest.CoreAbstArchiveHstryEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.task.CoreAbstBsnsHstryArchiveExecTask;
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
import org.adorsys.adprocmt.rest.PrcmtDeliveryHstryArchiveEJB;
import org.adorsys.adprocmt.rest.PrcmtDeliveryInjector;

@Stateless
public class PrcmtDlvryHstryArchiveExecTask extends CoreAbstBsnsHstryArchiveExecTask<PrcmtDelivery, PrcmtDeliveryArchive, PrcmtDlvryItem, PrcmtDlvryItemArchive, PrcmtDeliveryHstry, PrcmtDeliveryHstryArchive, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr, PrcmtPrcssgStep> {

	@Inject
	private PrcmtDeliveryInjector injector;
	@Inject
	private PrcmtDeliveryHstryArchiveEJB archiveEjb;
	@EJB
	private PrcmtBatch batch;
	@EJB
	private PrcmtDlvryHstryArchiveExecTask taskEjb;
	
	@Override
	protected CoreAbstBsnsObjInjector<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr> getInjector() {
		return injector;
	}

	@Override
	protected PrcmtDeliveryHstryArchive newHstryEntity() {
		return new PrcmtDeliveryHstryArchive();
	}

	@Override
	protected CoreAbstArchiveHstryEJB<PrcmtDeliveryHstryArchive> getHstryArchiveEjb() {
		return archiveEjb;
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
