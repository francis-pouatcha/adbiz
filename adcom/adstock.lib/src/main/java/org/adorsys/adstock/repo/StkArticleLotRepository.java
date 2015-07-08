package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkArticleLot.class)
public interface StkArticleLotRepository extends CoreAbstBsnsItemRepo<StkArticleLot>{}
