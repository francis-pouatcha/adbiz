package org.adorsys.adcore.repo;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.QueryResult;

public interface CoreAbstBsnsObjHstryRepo<E extends CoreAbstBsnsObjectHstry> extends EntityRepository<E, String>{

	public QueryResult<E> findByEntIdentif(String entIdentif);

	public QueryResult<E> findByEntIdentifAndEntStatus(String entIdentif, String entStatus);

	public QueryResult<E> findByEntIdentifAndIdBetween(String entIdentif, String idStart, String idEnd);
}
