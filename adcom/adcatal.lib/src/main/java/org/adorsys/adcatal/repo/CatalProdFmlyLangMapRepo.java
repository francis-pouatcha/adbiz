package org.adorsys.adcatal.repo;

import org.adorsys.adcatal.jpa.CatalProdFmlyLangMap;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalProdFmlyLangMap.class)
public interface CatalProdFmlyLangMapRepo extends CatalAbstArtLangMapRepo<CatalProdFmlyLangMap>{}
