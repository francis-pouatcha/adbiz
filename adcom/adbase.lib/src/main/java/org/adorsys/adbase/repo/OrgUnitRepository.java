package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = OrgUnit.class)
public interface OrgUnitRepository extends CoreAbstIdentifRepo<OrgUnit>
{
	public QueryResult<OrgUnit> findByRealmAndTenant(String identif, Boolean tenant);
}
