package org.adorsys.adaptmt.repo;

import org.adorsys.adaptmt.jpa.AptAptmt;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = AptAptmt.class)
public interface AptAptmtRepository extends EntityRepository<AptAptmt, String>
{
	
	@Query("SELECT a from AptAptmt AS a WHERE a.id > ?1 ORDER BY a.id ASC ")
	public QueryResult<AptAptmt> findNextAptAptmt(String id);
	
	@Query("SELECT a from AptAptmt AS a WHERE a.id < ?1 ORDER BY a.id ASC")
	public QueryResult<AptAptmt> findPreviousAptAptmt(String id);
	
}
