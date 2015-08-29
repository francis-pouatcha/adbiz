package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.UserWsRestriction;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = UserWsRestriction.class)
public interface UserWsRestrictionRepository extends CoreAbstIdentifRepo<UserWsRestriction>
{}
