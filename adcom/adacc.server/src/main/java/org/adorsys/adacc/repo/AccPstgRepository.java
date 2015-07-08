package org.adorsys.adacc.repo;

import org.adorsys.adacc.jpa.AccPstg;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = AccPstg.class)
public interface AccPstgRepository extends EntityRepository<AccPstg, String>
{
}
