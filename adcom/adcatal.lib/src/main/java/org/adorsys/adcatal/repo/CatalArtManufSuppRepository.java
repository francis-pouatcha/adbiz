package org.adorsys.adcatal.repo;

import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalArtManufSupp.class)
public interface CatalArtManufSuppRepository extends CoreAbstIdentifRepo<CatalArtManufSupp>{}
