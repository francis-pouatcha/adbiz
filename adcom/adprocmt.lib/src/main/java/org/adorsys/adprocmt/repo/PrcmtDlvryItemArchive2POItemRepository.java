package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemArchive2POItem;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryItemArchive2POItem.class)
public interface PrcmtDlvryItemArchive2POItemRepository extends CoreAbstIdentifRepo<PrcmtDlvryItemArchive2POItem> {}
