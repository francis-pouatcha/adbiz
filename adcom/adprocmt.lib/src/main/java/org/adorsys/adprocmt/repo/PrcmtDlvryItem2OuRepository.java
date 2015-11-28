package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2Ou;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryItem2Ou.class)
public interface PrcmtDlvryItem2OuRepository extends CoreAbstIdentifRepo<PrcmtDlvryItem2Ou>
{
	public List<PrcmtDlvryItem2Ou> findByDlvryItemNbr(String dlvryItemNbr);
}
