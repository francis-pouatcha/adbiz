package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.SecTermSession;
import org.adorsys.adcore.repo.CoreAbstEntityRepo;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SecTermSession.class)
public interface SecTermSessionRepository extends CoreAbstEntityRepo<SecTermSession>
{
}
