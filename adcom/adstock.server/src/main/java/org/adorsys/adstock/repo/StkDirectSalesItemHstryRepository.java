package org.adorsys.adstock.repo;

import org.adorsys.adstock.jpa.StkDirectSalesItemHstry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkDirectSalesItemHstry.class)
public interface StkDirectSalesItemHstryRepository extends EntityRepository<StkDirectSalesItemHstry, String>
{
	@Query("SELECT COUNT(e.id) FROM StkDirectSalesItemHstry e WHERE e.id=?1")
	public Long countById(String id);
}
