package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.SecTermRegist;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SecTermRegist.class)
public interface SecTermRegistRepository extends CoreAbstIdentifRepo<SecTermRegist>
{
}
