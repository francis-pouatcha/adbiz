package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtJob.class)
public interface PrcmtJobRepo extends CoreAbstEntityJobRepo<PrcmtJob> {};
