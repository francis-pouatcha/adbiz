package org.adorsys.adcost.repo;

import org.adorsys.adcore.repo.CoreAbstEntityCstrRepo;
import org.adorsys.adcost.jpa.CstStmntCstr;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CstStmntCstr.class)
public interface CstStmntCstrRepo extends CoreAbstEntityCstrRepo<CstStmntCstr>{}
