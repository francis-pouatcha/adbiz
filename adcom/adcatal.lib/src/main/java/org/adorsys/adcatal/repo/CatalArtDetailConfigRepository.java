package org.adorsys.adcatal.repo;

import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalArtDetailConfig.class)
public interface CatalArtDetailConfigRepository extends CatalAbstractArticleRepository<CatalArtDetailConfig>{}
