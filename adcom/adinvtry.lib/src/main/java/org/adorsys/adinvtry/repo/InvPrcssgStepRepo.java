package org.adorsys.adinvtry.repo;

import org.adorsys.adcore.repo.CoreAbstPrcssngStepRepo;
import org.adorsys.adinvtry.jpa.InvPrcssgStep;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvPrcssgStep.class)
public interface InvPrcssgStepRepo extends CoreAbstPrcssngStepRepo<InvPrcssgStep>{}
