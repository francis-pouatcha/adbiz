package org.adorsys.adbnsptnr.repo;

import java.util.Date;

import org.adorsys.adbnsptnr.jpa.BpIndivPtnrName;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BpIndivPtnrName.class)
public interface BpIndivPtnrNameRepository extends EntityRepository<BpIndivPtnrName, String>
{
	@Query("SELECT e FROM BpIndivPtnrName AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<BpIndivPtnrName> findByIdentif(String identif, Date validOn);

	@Query("SELECT e FROM BpIndivPtnrName AS e WHERE e.identif = ?1")
	public QueryResult<BpIndivPtnrName> findAllByIdentif(String identif);
}
