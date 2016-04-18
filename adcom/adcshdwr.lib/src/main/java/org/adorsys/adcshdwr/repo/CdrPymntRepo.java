package org.adorsys.adcshdwr.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcshdwr.jpa.CdrPymnt;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrPymnt.class)
public interface CdrPymntRepo extends CoreAbstIdentifRepo<CdrPymnt>
{}
