package org.adorsys.adcshdwr.repo;

import org.adorsys.adcore.repo.CoreAbstEvtRepo;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesEvt;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDrctSalesEvt.class)
public interface CdrDrctSalesEvtRepo extends CoreAbstEvtRepo<CdrDrctSalesEvt>
{}
