package org.adorsys.adcore.repo;

import org.adorsys.adcore.jpa.CoreAbstIdentifHstry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.QueryResult;

public interface CoreAbstIdentifObjectHstryRepo<E extends CoreAbstIdentifHstry> extends EntityRepository<E, String>{

	public QueryResult<E> findByEntIdentif(String entIdentif);

	public QueryResult<E> findByEntIdentifAndIdGreaterThan(String entIdentif, String idStart);
	
	public QueryResult<E> findByEntIdentifAndIdGreaterThanEquals(String entIdentif, String idStart);
	
	public QueryResult<E> findByEntIdentifAndEntStatus(String entIdentif, String entStatus);

	public QueryResult<E> findByIdBetween(String idStart, String idEnd);

	public QueryResult<E> findByIdGreaterThan(String idStart);

	public QueryResult<E> findByIdGreaterThanEquals(String idStart);
}
