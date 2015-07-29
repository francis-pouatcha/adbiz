package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adstock.jpa.StkMvntStep;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkMvntStep.class)
public interface StkMvntStepRepo extends CoreAbstEntityStepRepo<StkMvntStep>{}
