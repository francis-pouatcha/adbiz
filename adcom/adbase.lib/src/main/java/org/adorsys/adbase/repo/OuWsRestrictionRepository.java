package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.OuWsRestriction;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = OuWsRestriction.class)
public interface OuWsRestrictionRepository extends CoreAbstIdentifRepo<OuWsRestriction>{}