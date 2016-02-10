package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstryArchive;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDeliveryHstryArchive.class)
public interface PrcmtDeliveryHstryArchiveRepository extends CoreAbstBsnsObjHstryRepo<PrcmtDeliveryHstryArchive>{}
