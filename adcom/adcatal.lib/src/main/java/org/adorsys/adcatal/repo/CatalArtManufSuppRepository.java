package org.adorsys.adcatal.repo;

import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalArtManufSupp.class)
public interface CatalArtManufSuppRepository extends CoreAbstIdentifDataRepo<CatalArtManufSupp>{}
