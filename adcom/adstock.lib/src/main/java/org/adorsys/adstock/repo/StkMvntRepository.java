package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adstock.jpa.StkMvnt;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkMvnt.class)
public interface StkMvntRepository extends CoreAbstBsnsItemRepo<StkMvnt>{}
