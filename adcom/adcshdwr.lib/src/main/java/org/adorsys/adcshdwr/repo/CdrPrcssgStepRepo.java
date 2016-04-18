package org.adorsys.adcshdwr.repo;

import org.adorsys.adcore.repo.CoreAbstPrcssngStepRepo;
import org.adorsys.adcshdwr.jpa.CdrPrcssgStep;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrPrcssgStep.class)
public interface CdrPrcssgStepRepo extends CoreAbstPrcssngStepRepo<CdrPrcssgStep>{}
