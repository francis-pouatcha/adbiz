package org.adorsys.adcshdwr.repo;

import org.adorsys.adcshdwr.jpa.CdrPymntItem;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrPymntItem.class)
public interface CdrPymntItemRepository extends EntityRepository<CdrPymntItem, String>
{

	/**
	 * countByPymntNbr.
	 *
	 * @param pymntNbr
	 */
	public QueryResult<CdrPymntItem> findByPymntNbr(String pymntNbr);
}
