package org.adorsys.adcore.repo;

import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.apache.deltaspike.data.api.QueryResult;

public interface CoreAbstEntityJobRepo<E extends CoreAbstEntityJob> extends CoreAbstIdentifRepo<E>{

	public QueryResult<E> findByEntIdentif(String entIdentif);

	public QueryResult<E> findByEntIdentifAndJobStatus(String entIdentif, String jobStatus);

	public QueryResult<E> findByJobStatusAndStartTimeIsNullAndEndTimeIsNull(String jobStatus);

	public QueryResult<E> findByJobStatusAndStartTimeIsNotNullAndEndTimeIsNull(String jobStatus);

	public QueryResult<E> findByJobStatusAndStartTimeLessThanAndEndTimeIsNull(String jobStatus);
}
