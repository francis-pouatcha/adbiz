package org.adorsys.adcore.repo;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectHstry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.QueryResult;

public interface CoreAbstIdentifObjectHstryRepo<E extends CoreAbstIdentifObjectHstry> extends EntityRepository<E, String>{

	public QueryResult<E> findByEntIdentif(String entIdentif);

	public QueryResult<E> findByEntIdentifAndEntStatus(String entIdentif, String entStatus);
}
