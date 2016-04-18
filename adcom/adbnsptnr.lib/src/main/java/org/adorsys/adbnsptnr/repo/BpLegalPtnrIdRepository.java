package org.adorsys.adbnsptnr.repo;

import java.util.Date;

import org.adorsys.adbnsptnr.jpa.BpLegalPtnrId;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BpLegalPtnrId.class)
public interface BpLegalPtnrIdRepository extends EntityRepository<BpLegalPtnrId, String>
{
	@Query("SELECT e FROM BpLegalPtnrId AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<BpLegalPtnrId> findByIdentif(String identif, Date validOn);

	@Query("SELECT e FROM BpLegalPtnrId AS e WHERE e.identif = ?1")
	public QueryResult<BpLegalPtnrId> findAllByIdentif(String identif);
}
