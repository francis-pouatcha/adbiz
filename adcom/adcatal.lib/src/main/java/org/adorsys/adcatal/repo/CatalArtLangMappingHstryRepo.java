package org.adorsys.adcatal.repo;

import org.adorsys.adcatal.jpa.CatalArtLangMappingHstry;
import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalArtLangMappingHstry.class)
public interface CatalArtLangMappingHstryRepo extends CoreAbstIdentifObjectHstryRepo<CatalArtLangMappingHstry>{}
