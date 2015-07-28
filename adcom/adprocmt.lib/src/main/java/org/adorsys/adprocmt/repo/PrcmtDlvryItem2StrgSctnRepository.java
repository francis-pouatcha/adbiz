package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctn;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryItem2StrgSctn.class)
public interface PrcmtDlvryItem2StrgSctnRepository extends EntityRepository<PrcmtDlvryItem2StrgSctn, String>
{
	public List<PrcmtDlvryItem2StrgSctn> findByDlvryItemNbr(String dlvryItemNbr);
}
