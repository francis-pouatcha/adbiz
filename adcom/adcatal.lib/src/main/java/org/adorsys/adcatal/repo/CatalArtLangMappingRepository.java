package org.adorsys.adcatal.repo;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcore.repo.CoreAbstLangObjectRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalArtLangMapping.class)
public interface CatalArtLangMappingRepository extends CoreAbstLangObjectRepo<CatalArtLangMapping>{}
