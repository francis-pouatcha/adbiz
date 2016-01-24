package org.adorsys.adinvtry.task;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.rest.CoreAbstArchiveHstryEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstEntityJobExecutor;
import org.adorsys.adcore.task.CoreAbstBsnsHstryArchiveExecTask;
import org.adorsys.adcore.task.CoreAbstEntityBatch;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryArchive;
import org.adorsys.adinvtry.jpa.InvInvtryCstr;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryHstryArchive;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryItemArchive;
import org.adorsys.adinvtry.jpa.InvJob;
import org.adorsys.adinvtry.jpa.InvPrcssgStep;
import org.adorsys.adinvtry.jpa.InvStep;
import org.adorsys.adinvtry.rest.InvInvtryBatch;
import org.adorsys.adinvtry.rest.InvInvtryHstryArchiveEJB;
import org.adorsys.adinvtry.rest.InvInvtryInjector;

@Stateless
public class InvInvtryHstryArchiveExecTask extends CoreAbstBsnsHstryArchiveExecTask<InvInvtry, InvInvtryArchive, InvInvtryItem, InvInvtryItemArchive, InvInvtryHstry, InvInvtryHstryArchive, InvJob, InvStep, InvInvtryCstr, InvPrcssgStep> {

	@Inject 
	private InvInvtryInjector injector;
	@Inject
	private InvInvtryHstryArchiveEJB archiveEjb;
	@EJB
	private InvInvtryBatch batch;
	@EJB
	private InvInvtryHstryArchiveExecTask taskEjb;
	
	@Override
	protected CoreAbstBsnsObjInjector<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr> getInjector() {
		return injector;
	}

	@Override
	protected InvInvtryHstryArchive newHstryEntity() {
		return new InvInvtryHstryArchive();
	}

	@Override
	protected CoreAbstArchiveHstryEJB<InvInvtryHstryArchive> getHstryArchiveEjb() {
		return archiveEjb;
	}

	@Override
	protected CoreAbstEntityBatch<InvJob, InvStep, InvPrcssgStep> getBatch() {
		return batch;
	}

	@Override
	protected CoreAbstEntityJobExecutor<InvJob, InvStep, InvPrcssgStep> getEjb() {
		return taskEjb;
	}

}
