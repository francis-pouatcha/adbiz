package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstEvtRepo;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderEvt;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtProcOrderEvt.class)
public interface PrcmtProcOrderEvtRepository extends CoreAbstEvtRepo<PrcmtProcOrderEvt>{}
