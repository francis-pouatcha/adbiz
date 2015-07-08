package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryJob;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDeliveryJob.class)
public interface PrcmtDeliveryJobRepo extends CoreAbstEntityJobRepo<PrcmtDeliveryJob> {};
