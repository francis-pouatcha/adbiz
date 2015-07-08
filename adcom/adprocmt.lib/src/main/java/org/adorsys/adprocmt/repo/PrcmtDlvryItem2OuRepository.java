package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2Ou;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryItem2Ou.class)
public interface PrcmtDlvryItem2OuRepository extends EntityRepository<PrcmtDlvryItem2Ou, String>
{
	public List<PrcmtDlvryItem2Ou> findByDlvryItemNbr(String dlvryItemNbr);
}
