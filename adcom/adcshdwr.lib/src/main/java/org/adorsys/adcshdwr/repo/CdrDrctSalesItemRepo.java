package org.adorsys.adcshdwr.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItem;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDrctSalesItem.class)
public interface CdrDrctSalesItemRepo extends CoreAbstBsnsItemRepo<CdrDrctSalesItem>{}
