package org.adorsys.adcatal.repo;

import org.adorsys.adcatal.jpa.CatalArticle;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalArticle.class)
public interface CatalArticleRepository extends CatalAbstractArticleRepository<CatalArticle>{}
