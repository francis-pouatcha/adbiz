package org.adorsys.adstock.repo;

import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkArticleLot2StrgSctn.class)
public interface StkArticleLot2StrgSctnRepository extends StkAbstArtLot2SctnRepository<StkArticleLot2StrgSctn>{}
