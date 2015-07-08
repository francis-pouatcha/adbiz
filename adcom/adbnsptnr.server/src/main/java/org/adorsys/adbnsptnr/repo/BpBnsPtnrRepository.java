package org.adorsys.adbnsptnr.repo;

import org.adorsys.adbnsptnr.jpa.BpBnsPtnr;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BpBnsPtnr.class)
public interface BpBnsPtnrRepository extends EntityRepository<BpBnsPtnr, String>
{
	
	@Query("SELECT p FROM BpBnsPtnr AS p WHERE p.ptnrNbr = ?1")
	public QueryResult<BpBnsPtnr> findByPtnrNbr(String ptnrNbr);
	
	@Query("SELECT p FROM BpBnsPtnr AS p WHERE p.fullName = ?1")
	public QueryResult<BpBnsPtnr> findByFullName(String fullName);
}
