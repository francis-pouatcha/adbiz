package org.adorsys.adcost.repo;

import org.adorsys.adcost.jpa.CstActivityCenter;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CstActivityCenter.class)
public interface CstActivityCenterRepository extends EntityRepository<CstActivityCenter, String>
{
}
