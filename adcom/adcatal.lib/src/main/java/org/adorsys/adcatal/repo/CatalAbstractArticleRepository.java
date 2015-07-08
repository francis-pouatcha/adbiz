package org.adorsys.adcatal.repo;

import org.adorsys.adcatal.jpa.CatalAbstractArticle;
import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalArticle.class)
public interface CatalAbstractArticleRepository<E extends CatalAbstractArticle> extends CoreAbstIdentifDataRepo<E>{}
