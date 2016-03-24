package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adprocmt.jpa.PrcmtDlvryArchive2Ou;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryArchive2Ou.class)
public interface PrcmtDlvryArchive2OuRepository extends CoreAbstIdentifRepo<PrcmtDlvryArchive2Ou>{}
