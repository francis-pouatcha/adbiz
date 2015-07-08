package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtPOItem.class)
public interface PrcmtPOItemRepository extends CoreAbstBsnsItemRepo<PrcmtPOItem>{}
