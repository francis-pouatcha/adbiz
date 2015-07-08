package org.adorsys.adcshdwr.repo;

import org.adorsys.adcore.repo.CoreAbstEntityCstrRepo;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesCstr;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDrctSalesCstr.class)
public interface CdrDrctSalesCstrRepo extends CoreAbstEntityCstrRepo<CdrDrctSalesCstr>
{}
