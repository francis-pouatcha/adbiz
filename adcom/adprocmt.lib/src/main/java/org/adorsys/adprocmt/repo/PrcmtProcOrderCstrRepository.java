package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstEntityCstrRepo;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderCstr;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtProcOrderCstr.class)
public interface PrcmtProcOrderCstrRepository extends CoreAbstEntityCstrRepo<PrcmtProcOrderCstr>{}
