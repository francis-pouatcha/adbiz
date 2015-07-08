package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDeliveryHstry.class)
public interface PrcmtDeliveryHstryRepository extends CoreAbstBsnsObjHstryRepo<PrcmtDeliveryHstry>{}
