package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.OuType;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = OuType.class)
public interface OuTypeRepository extends CoreAbstIdentifRepo<OuType>{}
