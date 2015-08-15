package org.adorsys.adcost.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcost.jpa.CstStmnt;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CstStmnt.class)
public interface CstStmntRepo extends CoreAbstBsnsObjectRepo<CstStmnt>
{
}
