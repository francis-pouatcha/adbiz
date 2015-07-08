package org.adorsys.adbase.repo;

import java.util.Date;
import java.util.List;

import org.adorsys.adbase.jpa.OuType;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = OuType.class)
public interface OuTypeRepository extends EntityRepository<OuType, String>
{
	@Query("SELECT e FROM OuType AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<OuType> findByIdentif(String identif, Date validOn);
	
	@Query("SELECT e FROM OuType AS e WHERE e.validFrom <= ?1 AND (e.validTo IS NULL OR e.validTo > ?1)")
	public List<OuType> findActifsFrom(Date validFrom);

	@Query("SELECT COUNT(e) FROM OuType AS e WHERE e.validFrom <= ?1 AND (e.validTo IS NULL OR e.validTo > ?1)")
	public Long countActifsFrom(Date validFrom);
}
