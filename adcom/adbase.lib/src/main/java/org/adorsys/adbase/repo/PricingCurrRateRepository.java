package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.PricingCurrRate;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PricingCurrRate.class)
public interface PricingCurrRateRepository extends CoreAbstIdentifRepo<PricingCurrRate>{}
