package org.adorsys.adsales.repo;

import org.adorsys.adsales.jpa.SlsSOPtnr;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsSOPtnr.class)
public interface SlsSOPtnrRepository extends EntityRepository<SlsSOPtnr, String>
{
	
	@Query("SELECT p FROM SlsSOPtnr AS p WHERE p.ptnrNbr = ?1")
	public QueryResult<SlsSOPtnr> findByPtnrNbr(String ptnrNbr);
	
	@Query("SELECT p FROM SlsSOPtnr AS p WHERE p.soNbr = ?1")
	public QueryResult<SlsSOPtnr> findBySoNbr(String soNbr);
	
	
}
