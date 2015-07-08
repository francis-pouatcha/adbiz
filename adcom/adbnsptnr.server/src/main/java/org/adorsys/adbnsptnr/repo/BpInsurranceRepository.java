package org.adorsys.adbnsptnr.repo;

import java.util.Date;

import org.adorsys.adbnsptnr.jpa.BpInsurrance;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BpInsurrance.class)
public interface BpInsurranceRepository extends EntityRepository<BpInsurrance, String>
{
	@Query("SELECT e FROM BpInsurrance AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<BpInsurrance> findByIdentif(String identif, Date validOn);
}
