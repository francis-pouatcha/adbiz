package org.adorsys.adcore.repo;

import java.util.Date;

import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.apache.deltaspike.data.api.QueryResult;

public interface CoreAbstEntityStepRepo<E extends CoreAbstEntityStep> extends CoreAbstIdentifRepo<E>{

	public QueryResult<E> findByJobIdentif(String entIdentif);

	public QueryResult<E> findByJobIdentifAndStartedIsNullAndEndedIsNull(String entIdentif);

	public QueryResult<E> findByJobIdentifAndStartedIsNotNullAndEndedIsNull(String entIdentif);

	public QueryResult<E> findByStartedIsNotNullAndEndedIsNullAndLeaseEndLessThan(Date now);

	public QueryResult<E> findByJobIdentifAndStartedIsNullAndSchdldStartLessThan(Date now);
}
