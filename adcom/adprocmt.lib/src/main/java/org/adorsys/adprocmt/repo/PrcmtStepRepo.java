package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtStep.class)
public interface PrcmtStepRepo extends CoreAbstEntityStepRepo<PrcmtStep>{}
