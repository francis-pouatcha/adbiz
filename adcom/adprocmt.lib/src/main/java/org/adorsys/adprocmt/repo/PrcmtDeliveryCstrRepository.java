package org.adorsys.adprocmt.repo;

import org.adorsys.adcore.repo.CoreAbstEntityCstrRepo;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryCstr;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDeliveryCstr.class)
public interface PrcmtDeliveryCstrRepository extends CoreAbstEntityCstrRepo<PrcmtDeliveryCstr>{}
