package org.adorsys.adbase.repo;

import java.util.Date;

import org.adorsys.adbase.jpa.Locality;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = Locality.class)
public interface LocalityRepository extends EntityRepository<Locality, String>
{
	@Query("SELECT e FROM Locality AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<Locality> findByIdentif(String identif, Date validOn);
	
	@Query("SELECT e FROM Locality AS e WHERE e.validFrom <= ?1 AND (e.validTo IS NULL OR e.validTo > ?1)")
	public abstract QueryResult<Locality> findActiveLocality(Date currentDate);
	
	@Query("SELECT COUNT(e) FROM Locality AS e WHERE e.validFrom <= ?1 AND (e.validTo IS NULL OR e.validTo > ?1) ")
	public abstract Long countActiveLocality(Date currentDate);
}