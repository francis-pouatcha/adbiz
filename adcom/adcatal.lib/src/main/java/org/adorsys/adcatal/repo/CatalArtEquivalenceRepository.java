package org.adorsys.adcatal.repo;

import org.adorsys.adcatal.jpa.CatalArtEquivalence;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalArtEquivalence.class)
public interface CatalArtEquivalenceRepository extends CoreAbstIdentifRepo<CatalArtEquivalence>
{}
