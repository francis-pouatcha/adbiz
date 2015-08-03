package org.adorsys.adcore.jpa;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * This defines a task to be executed on an entity.
 *  
 * @author francis
 *
 */
@MappedSuperclass
public class CoreAbstEntityStep extends CoreAbstIdentifObject {
	private static final long serialVersionUID = -5970441290339654378L;

	/*
	 * This is the identifier of the target entity, for which this constraint has been created.
	 */
	@Column
	@NotNull
	private String entIdentif;

	/*
	 * Delimitation of the partition of this step. Allow for 
	 * map reduce processing of large populations.
	 */
	@Column
	private String stepStartId;
	/*
	 * Delimitation of the partition of this step. Allow for 
	 * map reduce processing of large populations.
	 */
	@Column
	private String stepEndId;

	/*
	 * Identity of the last sucessful transaction. We assume that
	 * there is an order in the execution, so that, the executor
	 * can process with this step after a recovery.
	 */
	@Column
	private String synchPointStart;
	
	/*
	 * Identity of the last sucessful transaction. We assume that
	 * there is an order in the execution, so that, the executor
	 * can process with this step after a recovery.
	 */
	@Column
	private String synchPointEnd;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date schdldStart;
	
	/*
	 * The step processing started at this time.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date started;

	/*
	 * The lease end. X time unit after this time, the processor
	 * should be considered dead.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date leaseEnd;

	/*
	 * The step processing ended at this time.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date ended;
	
	/*
	 * THe UID of the process doing the work. THis is to avoid multiple processing
	 * in distributed environments.
	 */
	@Column
	private String stepOwner;

	@Column
	@NotNull
	private String executorId;

	@Column
	private String preceedingStep;
	
	@Column
	private Date rcvrySchdlDt;
	
	@Column
	@Size(max=256)
	private String errror;
	
	@Override
	protected String makeIdentif() {
		return UUID.randomUUID().toString();
	}

	public String getEntIdentif() {
		return entIdentif;
	}

	public void setEntIdentif(String entIdentif) {
		this.entIdentif = entIdentif;
	}

	public String getStepStartId() {
		return stepStartId;
	}

	public void setStepStartId(String stepStartId) {
		this.stepStartId = stepStartId;
	}

	public String getStepEndId() {
		return stepEndId;
	}

	public void setStepEndId(String stepEndId) {
		this.stepEndId = stepEndId;
	}

	public String getSynchPointStart() {
		return synchPointStart;
	}

	public void setSynchPointStart(String synchPointStart) {
		this.synchPointStart = synchPointStart;
	}

	public String getSynchPointEnd() {
		return synchPointEnd;
	}

	public void setSynchPointEnd(String synchPointEnd) {
		this.synchPointEnd = synchPointEnd;
	}

	public Date getStarted() {
		return started;
	}

	public void setStarted(Date started) {
		this.started = started;
	}

	public Date getLeaseEnd() {
		return leaseEnd;
	}

	public void setLeaseEnd(Date leaseEnd) {
		this.leaseEnd = leaseEnd;
	}

	public Date getEnded() {
		return ended;
	}

	public void setEnded(Date ended) {
		this.ended = ended;
	}

	public String getStepOwner() {
		return stepOwner;
	}

	public void setStepOwner(String stepOwner) {
		this.stepOwner = stepOwner;
	}

	public Date getSchdldStart() {
		return schdldStart;
	}

	public void setSchdldStart(Date schdldStart) {
		this.schdldStart = schdldStart;
	}

	public String getExecutorId() {
		return executorId;
	}

	public void setExecutorId(String executorId) {
		this.executorId = executorId;
	}

	public String getPreceedingStep() {
		return preceedingStep;
	}

	public void setPreceedingStep(String preceedingStep) {
		this.preceedingStep = preceedingStep;
	}

	public Date getRcvrySchdlDt() {
		return rcvrySchdlDt;
	}

	public void setRcvrySchdlDt(Date rcvrySchdlDt) {
		this.rcvrySchdlDt = rcvrySchdlDt;
	}

	public String getErrror() {
		return errror;
	}

	public void setErrror(String errror) {
		this.errror = errror;
	}
}