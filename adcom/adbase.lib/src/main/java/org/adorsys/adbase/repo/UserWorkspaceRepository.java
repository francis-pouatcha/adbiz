package org.adorsys.adbase.repo;

import java.util.Date;

import org.adorsys.adbase.jpa.UserWorkspace;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = UserWorkspace.class)
public interface UserWorkspaceRepository extends EntityRepository<UserWorkspace, String>
{
	@Query("SELECT e FROM UserWorkspace AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<UserWorkspace> findByIdentif(String identif, Date validOn);

	@Query("SELECT e FROM UserWorkspace AS e WHERE e.loginName = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<UserWorkspace> findByLoginName(String loginName, Date now);
	
	@Query("SELECT e FROM UserWorkspace AS e WHERE e.loginName = ?1 AND e.ouWsIdentif = ?2 AND e.validFrom <= ?3 AND (e.validTo IS NULL OR e.validTo > ?3)")
	public QueryResult<UserWorkspace> findByLoginNameAndOuWsIdentif(String loginName, String ouWsIdentif, Date now);
}
