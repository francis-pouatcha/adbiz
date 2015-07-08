package org.adorsys.adbnsptnr.repo;

import java.util.Date;

import org.adorsys.adbnsptnr.jpa.BpPtnrCreditDtls;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BpPtnrCreditDtls.class)
public interface BpPtnrCreditDtlsRepository extends EntityRepository<BpPtnrCreditDtls, String>
{
	@Query("SELECT e FROM BpPtnrCreditDtls AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<BpPtnrCreditDtls> findByIdentif(String identif, Date validOn);
}
