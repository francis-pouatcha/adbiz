package org.adorsys.adstock.rest;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.rest.CatalArtLangMappingLookup;
import org.adorsys.adcore.event.EntityCreatedEvent;
import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.event.EntityUpdatedEvent;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adstock.jpa.StkArtLotSrchIdx;
import org.adorsys.adstock.jpa.StkArtLotSrchIdxJob;
import org.adorsys.adstock.jpa.StkArtLotSrchIdxJobTaskId;
import org.adorsys.adstock.jpa.StkArtLotSrchIdxStep;
import org.adorsys.adstock.jpa.StkArtLotSrchIdxStepTaskId;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.repo.StkArtLotSrchIdxRepository;

/**
 * TODO: also handle article name changed event.
 * 
 * @author francis
 *
 */
@Stateless
public class StkArtLotSrchIdxEJB extends
		CoreAbstIdentifiedEJB<StkArtLotSrchIdx> {
	@Inject
	private StkArtLotSrchIdxRepository repository;
	@Inject
	private StkArtLotSrchIdxLookup lookup;
	@EJB
	private StkArtLotSrchIdxJobEJB jobEjb;
	@EJB
	private StkArtLotSrchIdxJobLookup jobLookup;
	@EJB
	private StkArtLotSrchIdxEJB ejb;
	@EJB
	private CatalArtLangMappingLookup artLangMappingLookup;
	@EJB
	private StkArticleLot2StrgSctnLookup lot2StrgSctnLookup;
	@EJB
	private StkArticleLotLookup lotLookup;
	@EJB
	private StkArtLotSrchIdxStepEJB stepEJB;
	@EJB
	private StkArtLotSrchIdxStepLookup stepLookup;

	@Override
	protected CoreAbstIdentifRepo<StkArtLotSrchIdx> getRepo() {
		return repository;
	}
	
	public void handleStkArticleLot2StrgSctnCreatedEvent(@Observes @EntityCreatedEvent StkArticleLot2StrgSctn articleLot2StrgSctn){
		StkArtLotSrchIdxJob job = new StkArtLotSrchIdxJob();
		job.setEntIdentif(articleLot2StrgSctn.getIdentif());
		job.setCntnrIdentif(articleLot2StrgSctn.getIdentif());
		job.setExecutorId(StkArtLotSrchIdxEJB.class.getSimpleName());
		job.setTaskId(StkArtLotSrchIdxJobTaskId.INDEX.name());
		job.setIdentif(UUID.randomUUID().toString());// not compression.
		jobEjb.create(job);
	}

	public void handleArtNameChangedEvent(@Observes @EntityUpdatedEvent CatalArtLangMapping artLangMapping){
		handleArtNameEvent(artLangMapping);
	}
	public void handleArtNameCratedEvent(@Observes @EntityCreatedEvent CatalArtLangMapping artLangMapping){
		handleArtNameEvent(artLangMapping);
	}
	
	private void handleArtNameEvent(CatalArtLangMapping artLangMapping){
		StkArtLotSrchIdxJob job = new StkArtLotSrchIdxJob();
		job.setEntIdentif(artLangMapping.getIdentif());
		job.setCntnrIdentif(artLangMapping.getIdentif());
		job.setExecutorId(StkArtLotSrchIdxEJB.class.getSimpleName());
		job.setTaskId(StkArtLotSrchIdxJobTaskId.ART_NAME_CHANGE.name());
		job.setIdentif(UUID.randomUUID().toString());// not compression.
		jobEjb.create(job);
	}
	
	public void handleStkArticleLot2StrgSctnDeletedEvent(@Observes @EntityDeletedEvent StkArticleLot2StrgSctn articleLot2StrgSctn){
		List<StkArtLotSrchIdx> found = lookup.findBySectionAndLotPic(articleLot2StrgSctn.getSection(), articleLot2StrgSctn.getLotPic());
		for (StkArtLotSrchIdx stkArtLotSrchIdx : found) {
			deleteById(stkArtLotSrchIdx.getId());
		}
	}
	
	@Schedule(minute = "*", second="1/35" ,hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void processJobs() throws Exception {
		Long count = jobLookup.count();
		int start = 0;
		int max = 100;
		while(start<count){
			int firstResult = start;
			start+=max;
			List<StkArtLotSrchIdxJob> listAll = jobLookup.listAll(firstResult, max);
			for (StkArtLotSrchIdxJob stkArtLotSrchIdxJob : listAll) {
				if(StkArtLotSrchIdxJobTaskId.INDEX.equals(stkArtLotSrchIdxJob.getTaskId())){
					ejb.processIndexJob(stkArtLotSrchIdxJob);
				} else if (StkArtLotSrchIdxJobTaskId.ART_NAME_CHANGE.equals(stkArtLotSrchIdxJob.getTaskId())){
					ejb.processNameChangeJob(stkArtLotSrchIdxJob);					
				}
			}
		}
	}

	@Schedule(minute = "*", second="7/35" ,hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void processSteps() throws Exception {
		Long count = stepLookup.count();
		int start = 0;
		int max = 100;
		while(start<count){
			int firstResult = start;
			start+=max;
			List<StkArtLotSrchIdxStep> listAll = stepLookup.listAll(firstResult, max);
			for (StkArtLotSrchIdxStep step : listAll) {
				if(StkArtLotSrchIdxStepTaskId.ART_NAME_CHANGE.equals(step.getTaskId())){
					ejb.processNameChangeStep(step);					
				}
			}
		}
	}
	
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processIndexJob(StkArtLotSrchIdxJob job){
		job = jobLookup.findById(job.getId());
		if(job==null) return;
		StkArticleLot2StrgSctn lot2StrgSctn = lot2StrgSctnLookup.findByIdentif(job.getEntIdentif());
		if(lot2StrgSctn==null) {
			jobEjb.deleteById(job.getId());
			return;
		}
		StkArticleLot articleLot = lotLookup.findByIdentif(lot2StrgSctn.getLotPic());
		Long count = artLangMappingLookup.countByArtPic(lot2StrgSctn.getArtPic());
		
		int start = 0;
		int max = 100;
		while(start<count){
			int firstResult = start;
			start+=max;
			List<CatalArtLangMapping> list = artLangMappingLookup.findByArtPic(lot2StrgSctn.getArtPic(), firstResult, max);
			for (CatalArtLangMapping catalArtLangMapping : list) {
				StkArtLotSrchIdx stkArtLotSrchIdx = new StkArtLotSrchIdx();
				articleLot.copyTo(stkArtLotSrchIdx);
				lot2StrgSctn.copyTo(stkArtLotSrchIdx);
				stkArtLotSrchIdx.cleanId();
				stkArtLotSrchIdx.setArtName(catalArtLangMapping.getArtName());
				stkArtLotSrchIdx.setLangIso2(catalArtLangMapping.getLangIso2());
				stkArtLotSrchIdx.setArtLMIdx(catalArtLangMapping.getIdentif());
				create(stkArtLotSrchIdx);
			}
		}	
		jobEjb.deleteById(job.getId());
	}
	
	/**
	 * For a name change job, you must get all matching entries and update the name
	 * We will create Steps of hundred elements and delete the job.
	 * 
	 * @param job
	 */
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processNameChangeJob(StkArtLotSrchIdxJob job) {
		Long count = lookup.countByArtLMIdx(job.getEntIdentif());
		int start = 0;
		int max = 100;
		while(start<count){
			int firstResult = start;
			start+=max;
			// Find all entries with the article id and the language.
			List<StkArtLotSrchIdx> list = lookup.findByArtLMIdxOrdered(job.getEntIdentif(), firstResult, max);
			if(list.isEmpty()) continue;
			StkArtLotSrchIdxStep step = new StkArtLotSrchIdxStep();
			step.setCntnrIdentif(job.getId());
			step.setEntIdentif(job.getCntnrIdentif());
			step.setExecutorId(StkArtLotSrchIdxEJB.class.getSimpleName());
			step.setJobIdentif(job.getIdentif());
			LinkedList<StkArtLotSrchIdx> ll = new LinkedList<StkArtLotSrchIdx>(list);
			StkArtLotSrchIdx first = ll.getFirst();
			step.setStepStartId(first.getIdentif());
			StkArtLotSrchIdx last = ll.getLast();
			step.setStepEndId(last.getIdentif());
			step.setTaskId(StkArtLotSrchIdxStepTaskId.ART_NAME_CHANGE.name());
			stepEJB.create(step);
		}
		jobEjb.deleteById(job.getId());
	}
	
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processNameChangeStep(StkArtLotSrchIdxStep step) {
		Long count = lookup.countByIdentifBetween(step.getStepStartId(), step.getStepEndId());
		List<StkArtLotSrchIdx> list = lookup.findByIdentifBetween(step.getStepStartId(), step.getStepEndId(), 0, count.intValue());
		
		CatalArtLangMapping artLangMapping = null;
		for (StkArtLotSrchIdx stkArtLotSrchIdx : list) {
			if(artLangMapping==null){
				artLangMapping = artLangMappingLookup.findByIdentif(stkArtLotSrchIdx.getArtLMIdx());
				if(artLangMapping==null)break;
			}
			
			stkArtLotSrchIdx.setArtName(artLangMapping.getArtName());
			ejb.update(stkArtLotSrchIdx);
		}
		stepEJB.deleteById(step.getId());
	}
}
