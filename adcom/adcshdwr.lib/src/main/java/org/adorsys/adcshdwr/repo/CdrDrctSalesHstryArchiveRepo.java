package org.adorsys.adcshdwr.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesHstryArchive;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDrctSalesHstryArchive.class)
public interface CdrDrctSalesHstryArchiveRepo extends CoreAbstBsnsObjHstryRepo<CdrDrctSalesHstryArchive>{}
