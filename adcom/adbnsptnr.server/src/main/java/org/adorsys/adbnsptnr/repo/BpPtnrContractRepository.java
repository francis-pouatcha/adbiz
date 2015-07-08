package org.adorsys.adbnsptnr.repo;

import java.util.Date;

import org.adorsys.adbnsptnr.jpa.BpPtnrContract;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BpPtnrContract.class)
public interface BpPtnrContractRepository extends EntityRepository<BpPtnrContract, String>
{
	@Query("SELECT e FROM BpPtnrContract AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<BpPtnrContract> findByIdentif(String identif, Date validOn);

	@Query("SELECT e FROM BpPtnrContract AS e WHERE e.holderIdentif = ?1")
	public QueryResult<BpPtnrContract> findByHolderIdentif(String holderIdentif);
}
