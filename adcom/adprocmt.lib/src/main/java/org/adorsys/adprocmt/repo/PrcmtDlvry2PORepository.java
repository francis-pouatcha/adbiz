package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtDlvry2PO;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvry2PO.class)
public interface PrcmtDlvry2PORepository extends EntityRepository<PrcmtDlvry2PO, String>
{
	public List<PrcmtDlvry2PO> findByDlvryNbr(String dlvryNbr);
}
