package org.adorsys.adcost.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adcost.jpa.CstStmntHstry;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CstStmntHstry.class)
public interface CstStmntHstryRepo extends CoreAbstBsnsObjHstryRepo<CstStmntHstry>{}
