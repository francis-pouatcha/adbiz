package org.adorsys.adbnsptnr.repo;

import java.util.Date;

import org.adorsys.adbnsptnr.jpa.BpCtgryOfPtnr;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BpCtgryOfPtnr.class)
public interface BpCtgryOfPtnrRepository extends EntityRepository<BpCtgryOfPtnr, String>
{
	@Query("SELECT e FROM BpCtgryOfPtnr AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<BpCtgryOfPtnr> findByIdentif(String identif, Date validOn);
}
