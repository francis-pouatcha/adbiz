package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adstock.jpa.StkArticleLotHstry;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity=StkArticleLotHstry.class)
public interface StkArticleLotHstryRepo extends CoreAbstBsnsObjHstryRepo<StkArticleLotHstry>{}
