package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstPrcssngStepRepo;
import org.adorsys.adstock.jpa.StkPrcssgStep;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkPrcssgStep.class)
public interface StkPrcssgStepRepo extends CoreAbstPrcssngStepRepo<StkPrcssgStep>{}
