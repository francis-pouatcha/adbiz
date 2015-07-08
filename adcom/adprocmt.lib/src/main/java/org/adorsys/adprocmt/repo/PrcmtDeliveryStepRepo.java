package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryStep;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDeliveryStep.class)
public interface PrcmtDeliveryStepRepo extends CoreAbstEntityStepRepo<PrcmtDeliveryStep>{}
