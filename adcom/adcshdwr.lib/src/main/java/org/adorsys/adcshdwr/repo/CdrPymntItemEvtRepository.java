package org.adorsys.adcshdwr.repo;

import org.adorsys.adcshdwr.jpa.CdrPymntItemEvt;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrPymntItemEvt.class)
public interface CdrPymntItemEvtRepository extends EntityRepository<CdrPymntItemEvt, String>
{
}
