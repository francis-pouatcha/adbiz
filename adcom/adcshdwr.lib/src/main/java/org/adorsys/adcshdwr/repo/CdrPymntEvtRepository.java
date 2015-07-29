package org.adorsys.adcshdwr.repo;

import org.adorsys.adcshdwr.jpa.CdrPymntEvt;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrPymntEvt.class)
public interface CdrPymntEvtRepository extends EntityRepository<CdrPymntEvt, String> {}
