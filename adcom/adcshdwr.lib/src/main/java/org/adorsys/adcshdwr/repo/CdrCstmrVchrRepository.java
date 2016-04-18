package org.adorsys.adcshdwr.repo;

import java.util.List;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchr;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrCstmrVchr.class)
public interface CdrCstmrVchrRepository extends CoreAbstIdentifRepo<CdrCstmrVchr>
{
	List<CdrCstmrVchr> findByDsNbr(String dsNbr);
}
