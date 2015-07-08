package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtDlvryArtPrcssng;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryArtPrcssng.class)
public interface PrcmtDlvryArtPrcssngRepository extends EntityRepository<PrcmtDlvryArtPrcssng, String>
{
	public List<PrcmtDlvryArtPrcssng> findByDlvryNbr(String dlvryNbr);
}
