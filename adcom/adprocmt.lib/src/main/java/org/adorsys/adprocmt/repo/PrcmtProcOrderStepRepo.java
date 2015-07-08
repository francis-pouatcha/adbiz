package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderStep;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtProcOrderStep.class)
public interface PrcmtProcOrderStepRepo extends CoreAbstEntityStepRepo<PrcmtProcOrderStep>{}
