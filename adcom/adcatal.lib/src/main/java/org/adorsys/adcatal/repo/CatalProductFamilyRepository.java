package org.adorsys.adcatal.repo;

import org.adorsys.adcatal.jpa.CatalProductFamily;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalProductFamily.class)
public interface CatalProductFamilyRepository extends CoreAbstIdentifDataRepo<CatalProductFamily>
{}
