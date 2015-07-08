package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.SecTermSession;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SecTermSession.class)
public interface SecTermSessionRepository extends EntityRepository<SecTermSession, String>
{
}
