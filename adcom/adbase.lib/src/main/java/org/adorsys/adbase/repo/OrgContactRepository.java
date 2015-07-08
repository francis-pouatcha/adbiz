package org.adorsys.adbase.repo;

import java.util.Date;

import org.adorsys.adbase.jpa.OrgContact;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = OrgContact.class)
public interface OrgContactRepository extends EntityRepository<OrgContact, String>
{
	@Query("SELECT e FROM OrgContact AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<OrgContact> findByIdentif(String identif, Date validOn);

	@Query("SELECT e FROM OrgContact AS e WHERE e.ouIdentif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<OrgContact> findByOrgUnit(String ouIdentif, Date validOn);
	

	@Query("SELECT COUNT(e) FROM OrgContact AS e WHERE e.ouIdentif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public Long countByOrgUnit(String ouIdentif, Date validOn);
}
