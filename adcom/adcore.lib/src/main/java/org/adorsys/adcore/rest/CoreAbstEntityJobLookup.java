package org.adorsys.adcore.rest;

import java.util.List;

import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;

public abstract class CoreAbstEntityJobLookup<E extends CoreAbstEntityJob> extends CoreAbstIdentifLookup<E>{
	protected abstract CoreAbstEntityJobRepo<E> getJobRepo();
	protected CoreAbstIdentifRepo<E> getRepo(){
		return getJobRepo();
	};

	public Long countByEntIdentif(String entIdentif){
		return getJobRepo().findByEntIdentif(entIdentif).count();
	}
	public List<E> findByEntIdentif(String entIdentif, int start, int max){
		return getJobRepo().findByEntIdentif(entIdentif).firstResult(start).maxResults(max).getResultList();		
	}

	public Long countByEntIdentifAndJobStatus(String entIdentif, String jobStatus){
		return getJobRepo().findByEntIdentifAndJobStatus(entIdentif, jobStatus).count();
	}
	public List<E> findByEntIdentifAndJobStatus(String entIdentif, String jobStatus, int start, int max){
		return getJobRepo().findByEntIdentifAndJobStatus(entIdentif, jobStatus).firstResult(start).maxResults(max).getResultList();
	}

	public Long countByJobStatusAndStartTimeIsNullAndEndTimeIsNull(String jobStatus){
		return getJobRepo().findByJobStatusAndStartTimeIsNullAndEndTimeIsNull(jobStatus).count();
	}
	public List<E> findByJobStatusAndStartTimeIsNullAndEndTimeIsNull(String jobStatus, int start, int max){
		return getJobRepo().findByJobStatusAndStartTimeIsNullAndEndTimeIsNull(jobStatus).firstResult(start).maxResults(max).getResultList();
	}

	public Long countByJobStatusAndStartTimeIsNotNullAndEndTimeIsNull(String jobStatus){
		return getJobRepo().findByJobStatusAndStartTimeIsNotNullAndEndTimeIsNull(jobStatus).count();
	}
	public List<E> findByJobStatusAndStartTimeIsNotNullAndEndTimeIsNull(String jobStatus, int start, int max){
		return getJobRepo().findByJobStatusAndStartTimeIsNotNullAndEndTimeIsNull(jobStatus).firstResult(start).maxResults(max).getResultList();
	}

	public Long countByJobStatusAndStartTimeLessThanAndEndTimeIsNull(String jobStatus){
		return getJobRepo().findByJobStatusAndStartTimeLessThanAndEndTimeIsNull(jobStatus).count();
	}
	public List<E> findByJobStatusAndStartTimeLessThanAndEndTimeIsNull(String jobStatus, int start, int max){
		return getJobRepo().findByJobStatusAndStartTimeLessThanAndEndTimeIsNull(jobStatus).firstResult(start).maxResults(max).getResultList();
	}

	public Long countByJobStatus(String jobStatus){
		return getJobRepo().findByJobStatus(jobStatus).count();
	}
	public List<E> findByJobStatus(String jobStatus, int start, int max){
		return getJobRepo().findByJobStatus(jobStatus).firstResult(start).maxResults(max).getResultList();
	}
}
