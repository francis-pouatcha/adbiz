package org.adorsys.adcost.repo;

import org.adorsys.adcore.repo.CoreAbstPrcssngStepRepo;
import org.adorsys.adcost.jpa.CstPrcssgStep;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CstPrcssgStep.class)
public interface CstPrcssgStepRepo extends CoreAbstPrcssngStepRepo<CstPrcssgStep>{}
