package org.adorsys.adbase.repo;

import java.util.Date;

import org.adorsys.adbase.jpa.SecUserSession;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SecUserSession.class)
public interface SecUserSessionRepository extends EntityRepository<SecUserSession, String>
{
	@Query("SELECT e FROM SecUserSession AS e WHERE e.workspaceId = ?1 AND e.created <= ?2 AND (e.expires IS NULL OR e.expires > ?2)")
	public QueryResult<SecUserSession> findByWorkspaceId(String workspaceId,Date when);
}