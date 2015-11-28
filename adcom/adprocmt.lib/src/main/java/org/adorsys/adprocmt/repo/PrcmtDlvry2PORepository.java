package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2PO;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvry2PO.class)
public interface PrcmtDlvry2PORepository extends CoreAbstIdentifRepo<PrcmtDlvry2PO>
{
	public List<PrcmtDlvry2PO> findByDlvryNbr(String dlvryNbr);
}
