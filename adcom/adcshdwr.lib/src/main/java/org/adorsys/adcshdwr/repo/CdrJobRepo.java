package org.adorsys.adcshdwr.repo;

import org.adorsys.adcore.repo.CoreAbstEntityJobRepo;
import org.adorsys.adcshdwr.jpa.CdrJob;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrJob.class)
public interface CdrJobRepo extends CoreAbstEntityJobRepo<CdrJob>
{}
