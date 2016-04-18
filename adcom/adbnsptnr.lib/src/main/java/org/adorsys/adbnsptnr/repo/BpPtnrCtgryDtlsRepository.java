package org.adorsys.adbnsptnr.repo;

import java.util.Date;

import org.adorsys.adbnsptnr.jpa.BpPtnrCtgryDtls;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BpPtnrCtgryDtls.class)
public interface BpPtnrCtgryDtlsRepository extends EntityRepository<BpPtnrCtgryDtls, String>
{
	@Query("SELECT e FROM BpPtnrCtgryDtls AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<BpPtnrCtgryDtls> findByIdentif(String identif, Date validOn);

	@Query("SELECT e FROM BpPtnrCtgryDtls AS e WHERE e.ctgryCode = ?1")
	public QueryResult<BpPtnrCtgryDtls> findByCtgryCode(String ctgryCode);
}
