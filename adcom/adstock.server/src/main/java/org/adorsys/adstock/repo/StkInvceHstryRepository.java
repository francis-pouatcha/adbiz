package org.adorsys.adstock.repo;

import org.adorsys.adstock.jpa.StkInvceHstry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkInvceHstry.class)
public interface StkInvceHstryRepository extends EntityRepository<StkInvceHstry, String>
{
	@Query("SELECT COUNT(e.id) FROM StkInvceHstry e WHERE e.id=?1")
	public Long countById(String id);
}
