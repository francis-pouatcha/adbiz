package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryItem.class)
public interface PrcmtDlvryItemRepository extends CoreAbstBsnsItemRepo<PrcmtDlvryItem>{}
