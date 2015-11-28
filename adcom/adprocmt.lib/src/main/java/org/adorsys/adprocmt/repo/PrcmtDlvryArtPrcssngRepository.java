package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adprocmt.jpa.PrcmtDlvryArtPrcssng;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryArtPrcssng.class)
public interface PrcmtDlvryArtPrcssngRepository extends CoreAbstIdentifRepo<PrcmtDlvryArtPrcssng>
{
	public List<PrcmtDlvryArtPrcssng> findByDlvryNbr(String dlvryNbr);
}
