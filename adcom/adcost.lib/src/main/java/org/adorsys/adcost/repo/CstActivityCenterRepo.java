package org.adorsys.adcost.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcost.jpa.CstActivityCenter;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CstActivityCenter.class)
public interface CstActivityCenterRepo extends CoreAbstIdentifRepo<CstActivityCenter> {
}
