package org.adorsys.adcatal.repo;

import org.adorsys.adcatal.jpa.CatalFamilyFeatMaping;
import org.adorsys.adcore.repo.CoreAbstLangObjectRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalFamilyFeatMaping.class)
public interface CatalFamilyFeatMapingRepository extends CoreAbstLangObjectRepo<CatalFamilyFeatMaping>{}
