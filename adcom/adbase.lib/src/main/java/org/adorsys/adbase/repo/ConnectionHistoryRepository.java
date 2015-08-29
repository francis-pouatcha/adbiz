package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.ConnectionHistory;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = ConnectionHistory.class)
public interface ConnectionHistoryRepository extends CoreAbstIdentifRepo<ConnectionHistory>
{
	@Query("SELECT e FROM ConnectionHistory AS e WHERE e.loginName = ?1")
	public QueryResult<ConnectionHistory> findByLoginName(String loginName);
}
