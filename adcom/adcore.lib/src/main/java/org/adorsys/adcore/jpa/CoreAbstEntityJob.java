package org.adorsys.adcore.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Defines a job to be executed of an instance of a well defined entity.
 * 
 * The job creator, must make sure the map process is completed before it releases
 * the job to reduce workers.
 * 
 * - At creation, the job start time is first set to null.
 * - The map step is created at the same moment with the job.
 * - The mapper can use many transactions to partition the work to be done.
 * - Once the partitioning done, it is the task of the mapper to set the start time on job.
 * - We notice that the mapper failed when the leaseEnd time is not null and x minutes in the past.
 * - Another mapper job can take over the mapping work, compute where to start by looking at the 
 * 	last synchPoint in the mapping step. 
 * 
 * @author francis
 *
 */
@MappedSuperclass
public class CoreAbstEntityJob extends CoreAbstIdentifObject {
	private static final long serialVersionUID = -2140688506989577902L;

	/*
	 * This is the identifier of the target entity, for which this constraint
	 * has been created.
	 */
	@Column
	@NotNull
	private String entIdentif;

	@Column
	@NotNull
	private String executorId;

	@Column
	@NotNull
	private String taskId;
	
	@Column
	private String jobStatus;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;
	
	@Override
	protected String makeIdentif() {
		return entIdentif + "_" + executorId + "_" + taskId;
	}

	public String getEntIdentif() {
		return entIdentif;
	}

	public void setEntIdentif(String entIdentif) {
		this.entIdentif = entIdentif;
	}

	public String getExecutorId() {
		return executorId;
	}

	public void setExecutorId(String executorId) {
		this.executorId = executorId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}