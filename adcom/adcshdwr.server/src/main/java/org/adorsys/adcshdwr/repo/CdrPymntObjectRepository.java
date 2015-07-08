package org.adorsys.adcshdwr.repo;

import org.adorsys.adcshdwr.jpa.CdrPymntObject;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrPymntObject.class)
public interface CdrPymntObjectRepository extends EntityRepository<CdrPymntObject, String>
{
	public QueryResult<CdrPymntObject> findByOrigItemNbr(String origItemNbr);
}
