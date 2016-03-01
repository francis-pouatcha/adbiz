package org.adorsys.adcore.repo;

import java.util.Date;

import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.apache.deltaspike.data.api.QueryResult;

public interface CoreAbstEntityStepRepo<E extends CoreAbstEntityStep> extends CoreAbstIdentifRepo<E>{
	/**
	 * List not yet started jobs
	 */
	public QueryResult<E> findByStartedIsNullAndSchdldStartLessThan(Date now);

	/**
	 * Expired jobs. Must be recovered.
	 * 
	 * @param now
	 * @return
	 */
	public QueryResult<E> findByStartedIsNotNullAndEndedIsNullAndLeaseEndLessThan(Date now);

	/**
	 * Task scheduled for recovery.
	 * @param now
	 * @return
	 */
	public QueryResult<E> findByEndedIsNullAndRcvrySchdlIsNotNUllAndAndRcvrySchdlLessThan(Date now);
	
	/**
	 * Ended steps. Must be deleted.
	 * 
	 * @param now
	 * @return
	 */
	public QueryResult<E> findByEndedIsNotNull();

	/**
	 * Ended steps of a job. Must be deleted.
	 * 
	 * @param now
	 * @return
	 */
	public QueryResult<E> findByCntnrIdentifAndEndedIsNotNull(String cntnrIdentif);
	
	public QueryResult<E> findByCntnrIdentifAndEntIdentif(String cntnrIdentif,
			String entIdentif);

	public QueryResult<E> findByEndedIsNull();
	
	public QueryResult<E> findByEntIdentif(String entIdentif);
}
