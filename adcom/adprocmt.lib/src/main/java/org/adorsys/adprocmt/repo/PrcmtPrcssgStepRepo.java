package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstPrcssngStepRepo;
import org.adorsys.adprocmt.jpa.PrcmtPrcssgStep;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtPrcssgStep.class)
public interface PrcmtPrcssgStepRepo extends CoreAbstPrcssngStepRepo<PrcmtPrcssgStep>{}
