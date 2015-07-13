package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adstock.jpa.StkSection;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkSection.class)
public interface StkSectionRepository extends CoreAbstIdentifDataRepo<StkSection>
{
	public QueryResult<StkSection> findByParentCode(String parentCode);
}
