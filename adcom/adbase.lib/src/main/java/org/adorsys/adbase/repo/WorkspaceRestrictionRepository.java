package org.adorsys.adbase.repo;

import java.util.Date;

import org.adorsys.adbase.jpa.WorkspaceRestriction;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = WorkspaceRestriction.class)
public interface WorkspaceRestrictionRepository extends EntityRepository<WorkspaceRestriction, String>
{
	@Query("SELECT e FROM WorkspaceRestriction AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<WorkspaceRestriction> findByIdentif(String identif, Date validOn);
}
