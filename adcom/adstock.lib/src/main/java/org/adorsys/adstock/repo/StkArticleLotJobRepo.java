package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adstock.jpa.StkArticleLotJob;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkArticleLotJob.class)
public interface StkArticleLotJobRepo extends CoreAbstEntityJobRepo<StkArticleLotJob>{}
