package org.adorsys.adcshdwr.repo;

import org.adorsys.adcshdwr.jpa.CdrDsPymntItem;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDsPymntItem.class)
public interface CdrDsPymntItemRepository extends EntityRepository<CdrDsPymntItem, String>
{
	@Query("SELECT p FROM CdrDsPymntItem AS p WHERE p.dsNbr = ?1")
	QueryResult<CdrDsPymntItem> findByDsNbr(String dsNbr);
}
