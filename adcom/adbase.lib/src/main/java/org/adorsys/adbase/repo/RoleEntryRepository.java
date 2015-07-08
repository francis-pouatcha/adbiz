package org.adorsys.adbase.repo;

import java.util.Date;

import org.adorsys.adbase.jpa.RoleEntry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = RoleEntry.class)
public interface RoleEntryRepository extends EntityRepository<RoleEntry, String>
{
	@Query("SELECT e FROM RoleEntry AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<RoleEntry> findByIdentif(String identif, Date validOn);
}
