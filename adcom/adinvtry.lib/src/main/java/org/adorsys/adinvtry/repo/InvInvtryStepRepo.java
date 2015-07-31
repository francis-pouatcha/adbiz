package org.adorsys.adinvtry.repo;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adinvtry.jpa.InvInvtryStep;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryStep.class)
public interface InvInvtryStepRepo extends CoreAbstEntityStepRepo<InvInvtryStep>{}
