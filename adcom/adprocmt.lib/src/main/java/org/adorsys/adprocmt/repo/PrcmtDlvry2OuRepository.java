package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2Ou;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvry2Ou.class)
public interface PrcmtDlvry2OuRepository extends CoreAbstIdentifRepo<PrcmtDlvry2Ou>{}
