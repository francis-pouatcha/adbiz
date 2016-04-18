package org.adorsys.adbnsptnr.repo;

import java.util.Date;

import org.adorsys.adbnsptnr.jpa.BpInsrrncPpse;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BpInsrrncPpse.class)
public interface BpInsrrncPpseRepository extends EntityRepository<BpInsrrncPpse, String>
{
	@Query("SELECT e FROM BpInsrrncPpse AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<BpInsrrncPpse> findByIdentif(String identif, Date validOn);
}
