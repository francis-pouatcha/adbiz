package org.adorsys.adinvtry.repo;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adinvtry.jpa.InvStep;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvStep.class)
public interface InvStepRepo extends CoreAbstEntityStepRepo<InvStep>{}
