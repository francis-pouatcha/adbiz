package org.adorsys.adbase.repo;

import java.util.Date;

import org.adorsys.adbase.jpa.PermEntry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PermEntry.class)
public interface PermEntryRepository extends EntityRepository<PermEntry, String>
{
	@Query("SELECT e FROM PermEntry AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<PermEntry> findByIdentif(String identif, Date validOn);
}
