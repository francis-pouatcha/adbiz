package org.adorsys.adcshdwr.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesArchive;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDrctSalesArchive.class)
public interface CdrDrctSalesArchiveRepo extends CoreAbstBsnsObjectRepo<CdrDrctSalesArchive>{}
