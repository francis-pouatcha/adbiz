package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adstock.jpa.StkMvntHstry;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity=StkMvntHstry.class)
public interface StkMvntHstryRepo extends CoreAbstBsnsObjHstryRepo<StkMvntHstry>{}
