package org.adorsys.adbase.repo;

import java.util.Date;

import org.adorsys.adbase.jpa.SecTermRegist;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SecTermRegist.class)
public interface SecTermRegistRepository extends EntityRepository<SecTermRegist, String>
{
	@Query("SELECT e FROM SecTermRegist AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<SecTermRegist> findByIdentif(String identif, Date validOn);

	@Query("SELECT e FROM SecTermRegist AS e WHERE e.validFrom <= ?1 AND (e.validTo IS NULL OR e.validTo > ?1)")
	public abstract QueryResult<SecTermRegist> findActiveSecTerminal(Date currentDate);
	
	@Query("SELECT COUNT(e) FROM SecTermRegist AS e WHERE e.validFrom <= ?1 AND (e.validTo IS NULL OR e.validTo > ?1) ")
	public abstract Long countActiveSecTerminal(Date currentDate);
}
