package org.adorsys.adbase.repo;

import java.util.Date;

import org.adorsys.adbase.jpa.UserWsRestriction;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = UserWsRestriction.class)
public interface UserWsRestrictionRepository extends EntityRepository<UserWsRestriction, String>
{
	@Query("SELECT e FROM UserWsRestriction AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<UserWsRestriction> findByIdentif(String identif, Date validOn);
}
