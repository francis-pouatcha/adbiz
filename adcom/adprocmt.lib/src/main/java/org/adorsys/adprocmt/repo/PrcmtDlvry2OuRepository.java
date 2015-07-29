package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtDlvry2Ou;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvry2Ou.class)
public interface PrcmtDlvry2OuRepository extends EntityRepository<PrcmtDlvry2Ou, String>
{
	public List<PrcmtDlvry2Ou> findByDlvryNbr(String dlvryNbr);
}
