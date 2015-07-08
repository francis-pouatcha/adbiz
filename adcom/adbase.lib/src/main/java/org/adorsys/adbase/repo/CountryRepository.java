package org.adorsys.adbase.repo;

import java.util.Date;
import java.util.List;

import org.adorsys.adbase.jpa.Country;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = Country.class)
public interface CountryRepository extends EntityRepository<Country, String>
{
	@Query("SELECT e FROM Country AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<Country> findByIdentif(String identif, Date validOn);
	

	@Query("SELECT e FROM Country AS e WHERE UPPER(e.iso3) = UPPER(?1) AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<Country> findByIso3(String iso3, Date validOn);
	

	@Query("SELECT e FROM Country AS e WHERE e.validFrom <= ?1 AND (e.validTo IS NULL OR e.validTo > ?1)")
	public List<Country> findActicfCountrys(Date validOn);
}
