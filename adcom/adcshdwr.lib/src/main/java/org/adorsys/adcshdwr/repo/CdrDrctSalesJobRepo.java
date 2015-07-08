package org.adorsys.adcshdwr.repo;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesJob;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDrctSalesJob.class)
public interface CdrDrctSalesJobRepo extends CoreAbstEntityJobRepo<CdrDrctSalesJob>
{}
