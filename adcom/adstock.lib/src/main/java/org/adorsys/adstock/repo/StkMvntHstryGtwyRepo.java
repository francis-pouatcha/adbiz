package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adstock.jpa.StkMvntHstryGtwy;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity=StkMvntHstryGtwy.class)
public interface StkMvntHstryGtwyRepo extends CoreAbstBsnsObjHstryRepo<StkMvntHstryGtwy>{}
