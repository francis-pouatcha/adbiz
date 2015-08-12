package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adstock.jpa.StkStep;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkStep.class)
public interface StkStepRepo extends CoreAbstEntityStepRepo<StkStep>{}
