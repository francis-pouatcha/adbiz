package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adprocmt.jpa.PrcmtDlvryArchive2PO;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryArchive2PO.class)
public interface PrcmtDlvryArchive2PORepository extends CoreAbstIdentifRepo<PrcmtDlvryArchive2PO>{}
