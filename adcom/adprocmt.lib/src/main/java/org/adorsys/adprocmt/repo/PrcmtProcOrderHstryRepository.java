package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderHstry;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtProcOrderHstry.class)
public interface PrcmtProcOrderHstryRepository extends CoreAbstBsnsObjHstryRepo<PrcmtProcOrderHstry>{}
