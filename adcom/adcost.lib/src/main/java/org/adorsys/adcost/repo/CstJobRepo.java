package org.adorsys.adcost.repo;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adcost.jpa.CstJob;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CstJob.class)
public interface CstJobRepo extends CoreAbstEntityJobRepo<CstJob> {};
