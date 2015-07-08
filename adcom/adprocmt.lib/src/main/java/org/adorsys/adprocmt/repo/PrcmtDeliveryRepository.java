package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDelivery.class)
public interface PrcmtDeliveryRepository extends CoreAbstBsnsObjectRepo<PrcmtDelivery>{}
