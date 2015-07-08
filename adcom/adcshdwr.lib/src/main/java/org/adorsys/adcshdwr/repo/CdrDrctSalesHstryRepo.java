package org.adorsys.adcshdwr.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesHstry;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDrctSalesHstry.class)
public interface CdrDrctSalesHstryRepo extends CoreAbstBsnsObjHstryRepo<CdrDrctSalesHstry>{}
