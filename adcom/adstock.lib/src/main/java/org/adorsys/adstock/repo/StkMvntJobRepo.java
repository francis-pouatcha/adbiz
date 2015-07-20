package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adstock.jpa.StkMvntJob;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkMvntJob.class)
public interface StkMvntJobRepo extends CoreAbstEntityJobRepo<StkMvntJob>{}
