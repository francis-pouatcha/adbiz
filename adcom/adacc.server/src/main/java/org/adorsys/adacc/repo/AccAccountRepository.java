package org.adorsys.adacc.repo;

import org.adorsys.adacc.jpa.AccAccount;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = AccAccount.class)
public interface AccAccountRepository extends EntityRepository<AccAccount, String>
{
}
