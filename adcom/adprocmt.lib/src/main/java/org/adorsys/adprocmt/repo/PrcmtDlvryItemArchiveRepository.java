package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemArchive;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryItemArchive.class)
public interface PrcmtDlvryItemArchiveRepository extends CoreAbstBsnsItemRepo<PrcmtDlvryItemArchive>{}
