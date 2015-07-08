package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2POItem;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryItem2POItem.class)
public interface PrcmtDlvryItem2POItemRepository extends EntityRepository<PrcmtDlvryItem2POItem, String> {
	public List<PrcmtDlvryItem2POItem> findByDlvryItemNbr(String dlvryItemNbr);
}
