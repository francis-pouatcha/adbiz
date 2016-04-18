package org.adorsys.adcshdwr.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesItemArchive;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDrctSalesItemArchive.class)
public interface CdrDrctSalesItemArchiveRepo extends CoreAbstBsnsItemRepo<CdrDrctSalesItemArchive>{}
