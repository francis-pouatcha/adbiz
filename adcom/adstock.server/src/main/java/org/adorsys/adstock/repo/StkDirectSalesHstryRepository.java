package org.adorsys.adstock.repo;

import org.adorsys.adstock.jpa.StkDirectSalesHstry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkDirectSalesHstry.class)
public interface StkDirectSalesHstryRepository extends EntityRepository<StkDirectSalesHstry, String>
{
	@Query("SELECT COUNT(e.id) FROM StkDirectSalesHstry e WHERE e.id=?1")
	public Long countById(String id);
}
