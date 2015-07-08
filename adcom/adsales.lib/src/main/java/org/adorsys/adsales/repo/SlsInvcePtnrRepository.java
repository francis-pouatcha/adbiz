package org.adorsys.adsales.repo;

import org.adorsys.adsales.jpa.SlsInvcePtnr;
import org.adorsys.adsales.jpa.SlsSOPtnr;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsInvcePtnr.class)
public interface SlsInvcePtnrRepository extends EntityRepository<SlsInvcePtnr, String>
{
	
	@Query("SELECT p FROM SlsInvcePtnr AS p WHERE p.invceNbr = ?1")
	public QueryResult<SlsInvcePtnr> findByInvceNbr(String invceNbr);
}
