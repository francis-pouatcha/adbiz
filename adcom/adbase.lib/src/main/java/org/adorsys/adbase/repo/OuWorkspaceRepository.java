package org.adorsys.adbase.repo;

import java.util.Date;
import java.util.List;

import org.adorsys.adbase.jpa.OuWorkspace;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = OuWorkspace.class)
public interface OuWorkspaceRepository extends EntityRepository<OuWorkspace, String>
{
	@Query("SELECT e FROM OuWorkspace AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<OuWorkspace> findByIdentif(String identif, Date validOn);
	

	@Query("SELECT e FROM OuWorkspace AS e WHERE e.targetOuIdentif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public List<OuWorkspace> findByTargetOuIdentif(String targetOuIdentif, Date validOn);
}
