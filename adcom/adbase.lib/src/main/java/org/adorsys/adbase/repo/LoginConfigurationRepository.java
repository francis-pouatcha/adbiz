package org.adorsys.adbase.repo;

import java.math.BigDecimal;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.jpa.LoginConfiguration;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = LoginConfiguration.class)
public interface LoginConfigurationRepository extends
		EntityRepository<LoginConfiguration, String> {

	@Query("SELECT e FROM LoginConfiguration AS e WHERE e.identif = ?1")
	public QueryResult<LoginConfiguration> findByIdentif(String identif);

	@Query("SELECT e FROM LoginConfiguration AS e WHERE e.loginName = ?1")
	public QueryResult<LoginConfiguration> findByLogin(String loginName);

	@Query("SELECT e FROM LoginConfiguration AS e WHERE e.maxRebate = ?1")
	public QueryResult<LoginConfiguration> findByMaxRebate(BigDecimal maxRebate);

	@Query("SELECT l from LoginConfiguration AS l WHERE l.identif > ?1 ORDER BY l.identif ASC ")
	public QueryResult<LoginConfiguration> findNextLoginRebate(String identif);

	@Query("SELECT l from LoginConfiguration AS l WHERE l.identif < ?1 ORDER BY l.identif ASC ")
	public QueryResult<LoginConfiguration> findPreviousLoginRebate(String identif);

}
