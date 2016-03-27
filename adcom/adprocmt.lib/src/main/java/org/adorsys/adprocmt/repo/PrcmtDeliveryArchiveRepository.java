package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryArchive;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDeliveryArchive.class)
public interface PrcmtDeliveryArchiveRepository extends CoreAbstBsnsObjectRepo<PrcmtDeliveryArchive>{}
