package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderJob;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtProcOrderJob.class)
public interface PrcmtProcOrderJobRepo extends CoreAbstEntityJobRepo<PrcmtProcOrderJob> {};
