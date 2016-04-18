package org.adorsys.adcshdwr.repo;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adcshdwr.jpa.CdrStep;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrStep.class)
public interface CdrDrctSalesStepRepo extends CoreAbstEntityStepRepo<CdrStep>
{}
