package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.Login;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = Login.class)
public interface LoginRepository extends EntityRepository<Login, String>
{
	@Query("SELECT e FROM Login AS e WHERE e.identif = ?1")
	public QueryResult<Login> findByIdentif(String identif);

	@Query("SELECT e FROM Login AS e WHERE e.loginAlias = ?1")
	public QueryResult<Login> findByLoginAlias(String loginAlias);
	
	@Query("SELECT l from Login AS l WHERE l.loginName > ?1 ORDER BY l.loginName ASC ")
	public QueryResult<Login> findNextLogin(String loginName);
	
	@Query("SELECT l from Login AS l WHERE l.loginName < ?1 ORDER BY l.loginName ASC ")
	public QueryResult<Login> findPreviousLogin(String loginName);
}
