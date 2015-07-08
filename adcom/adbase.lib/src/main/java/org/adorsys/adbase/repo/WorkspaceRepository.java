package org.adorsys.adbase.repo;

import java.util.Date;

import org.adorsys.adbase.jpa.Workspace;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = Workspace.class)
public interface WorkspaceRepository extends EntityRepository<Workspace, String>
{
	@Query("SELECT e FROM Workspace AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<Workspace> findByIdentif(String identif, Date validOn);
}
