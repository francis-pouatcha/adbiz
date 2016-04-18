package org.adorsys.adbnsptnr.repo;

import org.adorsys.adbnsptnr.jpa.BpPtnrCtgry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BpPtnrCtgry.class)
public interface BpPtnrCtgryRepository extends EntityRepository<BpPtnrCtgry, String>
{
	@Query("SELECT e FROM BpPtnrCtgry AS e WHERE e.identif = ?1")
	public QueryResult<BpPtnrCtgry> findByIdentif(String identif);
}
