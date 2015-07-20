package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstEntityStepRepo;
import org.adorsys.adstock.jpa.StkArticleLotStep;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkArticleLotStep.class)
public interface StkArticleLotStepRepo extends CoreAbstEntityStepRepo<StkArticleLotStep>{}
