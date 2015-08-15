package org.adorsys.adcost.repo;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adcost.jpa.CstStep;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CstStep.class)
public interface CstStepRepo extends CoreAbstEntityStepRepo<CstStep>{}
