package org.adorsys.adcost.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcost.jpa.CstStmntItem;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CstStmntItem.class)
public interface CstStmntItemRepo extends CoreAbstBsnsItemRepo<CstStmntItem> {}
