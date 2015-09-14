package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.UserWorkspace;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = UserWorkspace.class)
public interface UserWorkspaceRepository extends CoreAbstIdentifRepo<UserWorkspace>
{
	@Query("SELECT e FROM UserWorkspace AS e WHERE e.loginName = ?1")
	public QueryResult<UserWorkspace> findByLoginName(String loginName);
	
	@Query("SELECT e FROM UserWorkspace AS e WHERE e.loginName = ?1 AND e.ouWsIdentif = ?2")
	public QueryResult<UserWorkspace> findByLoginNameAndOuWsIdentif(String loginName, String ouWsIdentif);
}
