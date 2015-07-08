package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtProcOrder.class)
public interface PrcmtProcOrderRepository extends CoreAbstBsnsObjectRepo<PrcmtProcOrder>{}
