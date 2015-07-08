package org.adorsys.adcshdwr.repo;

import java.util.List;

import org.adorsys.adcshdwr.jpa.CdrCstmrVchr;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrCstmrVchr.class)
public interface CdrCstmrVchrRepository extends EntityRepository<CdrCstmrVchr, String>
{
	List<CdrCstmrVchr> findByVchrNbr(String vchrNbr);
	
	List<CdrCstmrVchr> findByDsNbr(String dsNbr);
}
