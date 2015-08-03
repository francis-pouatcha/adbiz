package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adstock.jpa.StkJob;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkJob.class)
public interface StkJobRepo extends CoreAbstEntityJobRepo<StkJob>{}
