package org.adorsys.adcshdwr.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchrHstry;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrCstmrVchrHstry.class)
public interface CdrCstmrVchrHstryHstryRepo extends CoreAbstIdentifObjectHstryRepo<CdrCstmrVchrHstry>{}
