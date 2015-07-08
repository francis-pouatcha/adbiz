package org.adorsys.adcore.rest;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adcore.enums.CoreJobExecutorIdEnum;
import org.adorsys.adcore.enums.CoreJobStatusEnum;
import org.adorsys.adcore.enums.CoreJobTaskIdEnum;
import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.enums.CoreStepExecutorIdEnum;
import org.adorsys.adcore.event.EntityHstryEvent;
import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.apache.commons.lang3.time.DateUtils;

public abstract class CoreAbstBsnsObjectHstryEJB<E extends CoreAbstBsnsObject, 
	I extends CoreAbstBsnsItem, H extends CoreAbstBsnsObjectHstry, 
	J extends CoreAbstEntityJob, S extends CoreAbstEntityStep, C extends CoreAbstEntityCstr> 
	extends CoreAbstIdentifiedHstryEJB<H, E>{

	@Inject
	@EntityHstryEvent
	private Event<H> entityHistoryEvent;

	protected abstract CoreAbstBsnsObjectHstryLookup<H> getLookup();

	protected abstract CoreAbstEntityJobEJB<J> getJobEjb();

	protected abstract CoreAbstEntityJobLookup<J> getJobLookup();

	protected abstract CoreAbstEntityStepEJB<S> getStepEjb();

	protected abstract CoreAbstEntityStepLookup<S> getStepLookup();

	protected abstract CoreAbstBsnsItemBatch<E, I, H, J, S, C> getBatch();

	protected abstract H newHstryObj();
	protected abstract TermWsUserPrincipal getCallerPrincipal();

	public H create(H entity) {
		H saved = getRepo().save(entity);
		fireEvent(saved);
		return saved;
	}

	public H deleteById(String id) {
		H entity = getRepo().findBy(id);
		if (entity != null) {
			getRepo().remove(entity);
		}
		return entity;
	}

	public void deleteByEntIdentif(String invtryNbr, int first, int max) {
		List<H> list = getRepo().findByEntIdentif(invtryNbr).firstResult(first)
				.maxResults(max).getResultList();
		for (H hstry : list) {
			getRepo().remove(hstry);
		}
	}

	private static final int HSTRY_COUNT_TRESHOLD = 100;

	protected void handleEvent(H hstry) {
		if (CoreProcessStatusEnum.DELETED.name().equals(hstry.getEntStatus())) {
			processDeleteHstries(hstry);
		}
	}

	protected void processDeleteHstries(H hstry) {
		createDeleteJob(hstry);
	}
	

	private void createDeleteJob(H hstry) {
		J job = getJobEjb().newJobInstance();
		job.setExecutorId(CoreJobExecutorIdEnum.historyEJB.name());
		job.setExecutorId(CoreJobTaskIdEnum.DELETE.name());
		job.setJobStatus(CoreJobStatusEnum.INITIATED.name());
		getJobEjb().create(job);

		S s = getStepEjb().newStepInstance();
		s.setEntIdentif(hstry.getEntIdentif());
		s.setJobIdentif(job.getIdentif());
		s.setExecutorId(job.getExecutorId());
		s.setTaskId(CoreStepExecutorIdEnum.PREPARE_DELETE.name());
		s.setSchdldStart(DateUtils.addMinutes(new Date(), 1));
		getStepEjb().create(s);
	}

	private void prepareDeleteJob(String jobIdentif, String stepIdentif) {
		// use the get ejb interface to activate new transaction
		getBatch().lease(stepIdentif, 300);// 5 mins for the preparation.

		// Only do this job, if you controle the prepare job
		J job = getJobLookup().findByIdentif(jobIdentif);
		String entIdentif = job.getEntIdentif();
		Long itemsCount = getLookup().countByEntIdentif(entIdentif);
		int itemStart = 0;
		while (itemStart < itemsCount) {
			int firstResult = itemStart;
			itemStart += HSTRY_COUNT_TRESHOLD;
			List<H> list = getLookup().findByEntIdentifOrderByIdAsc(
					entIdentif, firstResult, HSTRY_COUNT_TRESHOLD);
			LinkedList<H> linkedList = new LinkedList<H>(list);

			S s = getStepEjb().newStepInstance();
			s.setEntIdentif(entIdentif);
			s.setJobIdentif(job.getIdentif());
			s.setStepStartId(linkedList.getFirst().getId());
			s.setStepEndId(linkedList.getLast().getId());
			s.setExecutorId(job.getExecutorId());
			s.setTaskId(CoreStepExecutorIdEnum.DELETE_ITEMS.name());
			s.setPreceedingStep(stepIdentif);
			getStepEjb().create(s);
		}

		S step = getStepLookup().findByIdentif(stepIdentif);// Refresh the step
															// object
		step.setEnded(new Date());
		getStepEjb().update(step);
	}

	public void processStep(S step) {
		String executorId = step.getExecutorId();
		if (CoreStepExecutorIdEnum.PREPARE_DELETE.name().equals(executorId)) {
			prepareDeleteJob(step.getJobIdentif(), step.getIdentif());
		} else if (CoreStepExecutorIdEnum.DELETE_ITEMS.name()
				.equals(executorId)) {
			deleteHstries(step.getJobIdentif(), step.getIdentif());
		}
	}

	private void deleteHstries(String jobIdentif, String stepIdentif) {
		getBatch().lease(stepIdentif, 300);// 5 mins for the preparation.

		// Only do this job, if you controle the prepare job
		J job = getJobLookup().findByIdentif(jobIdentif);
		S step = getStepLookup().findByIdentif(stepIdentif);// Refresh the step
															// object
		String entIdentif = job.getEntIdentif();
		Long count = getLookup().countByEntIdentifAndIdBetween(
				entIdentif, step.getStepStartId(), step.getStepEndId());
		if (count > 0) {
			List<H> list = getLookup().findByEntIdentifAndIdBetweenOrderByIdAsc(
							entIdentif, step.getStepStartId(),
							step.getStepEndId(), 0, count.intValue());
			for (H h : list) {
				deleteById(h.getId());
			}
		}
		step.setEnded(new Date());
		getStepEjb().update(step);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public H createHstryCommited(String entIdentif, String txStatus,
			String hstryType, String processingStep, String comment, String addInfo){
		return createItemHstry(entIdentif, null, txStatus, hstryType, processingStep, comment, addInfo);
	}
	
	public H createHstry(String entIdentif, String txStatus,
			String hstryType, String processingStep, String comment, String addInfo){
		return createItemHstry(entIdentif, null, txStatus, hstryType, processingStep, comment, addInfo);
	}
	public H createItemHstry(String entIdentif, String itemIdentif, String txStatus,
			String hstryType, String processingStep, String comment, String addInfo){
		TermWsUserPrincipal p = getCallerPrincipal();
		H hstry = newHstryObj();
		hstry.setComment(comment);
		hstry.setAddtnlInfo(addInfo);
		hstry.setEntIdentif(entIdentif);
		hstry.setEntStatus(txStatus);
		hstry.setHstryDt(new Date());
		hstry.setHstryType(hstryType);
		
		hstry.setOrignLogin(p.getName());
		hstry.setOrignWrkspc(p.getWorkspaceId());
		hstry.setProcStep(processingStep);
		return create(hstry);		
	}

	protected void fireEvent(H hstry){
		entityHistoryEvent.fire(hstry);
	}

}
