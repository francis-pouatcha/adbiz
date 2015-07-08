package org.adorsys.adstock.repo;

import java.util.Date;
import java.util.List;

import org.adorsys.adstock.jpa.StkSection;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkSection.class)
public interface StkSectionRepository extends EntityRepository<StkSection, String>
{
	@Query("SELECT e FROM StkSection AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<StkSection> findByIdentif(String identif, Date validOn);

	public List<StkSection> findByParentCode(String parentCode);

}
