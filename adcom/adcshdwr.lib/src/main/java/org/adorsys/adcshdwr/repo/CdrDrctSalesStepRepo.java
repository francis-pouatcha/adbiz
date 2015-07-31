package org.adorsys.adcshdwr.repo;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesStep;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDrctSalesStep.class)
public interface CdrDrctSalesStepRepo extends CoreAbstEntityStepRepo<CdrDrctSalesStep>
{}
