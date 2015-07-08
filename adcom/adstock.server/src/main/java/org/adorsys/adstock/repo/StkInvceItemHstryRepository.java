package org.adorsys.adstock.repo;

import org.adorsys.adstock.jpa.StkInvceItemHstry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkInvceItemHstry.class)
public interface StkInvceItemHstryRepository extends EntityRepository<StkInvceItemHstry, String>
{
	@Query("SELECT COUNT(e.id) FROM StkInvceItemHstry e WHERE e.id=?1")
	public Long countById(String id);
}
