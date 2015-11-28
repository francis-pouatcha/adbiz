package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2POItem;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryItem2POItem.class)
public interface PrcmtDlvryItem2POItemRepository extends CoreAbstIdentifRepo<PrcmtDlvryItem2POItem> {
	public List<PrcmtDlvryItem2POItem> findByDlvryItemNbr(String dlvryItemNbr);
}
