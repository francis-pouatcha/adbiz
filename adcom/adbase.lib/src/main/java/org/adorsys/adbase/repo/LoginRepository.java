package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = Login.class)
public interface LoginRepository extends CoreAbstIdentifRepo<Login>
{
}
