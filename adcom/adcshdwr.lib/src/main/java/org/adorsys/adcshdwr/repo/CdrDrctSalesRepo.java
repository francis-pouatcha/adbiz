package org.adorsys.adcshdwr.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDrctSales.class)
public interface CdrDrctSalesRepo extends CoreAbstBsnsObjectRepo<CdrDrctSales>{}
