package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.Locality;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = Locality.class)
public interface LocalityRepository extends CoreAbstIdentifRepo<Locality>{}