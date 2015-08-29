package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.ConverterCurrRate;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = ConverterCurrRate.class)
public interface ConverterCurrRateRepository extends CoreAbstIdentifRepo<ConverterCurrRate>{}
