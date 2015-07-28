package org.adorsys.adaptmt.repo;

import org.adorsys.adaptmt.jpa.AptAptmtRepport;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = AptAptmtRepport.class)
public interface AptAptmtRepportRepository extends
		EntityRepository<AptAptmtRepport, String> {

	@Query("SELECT a from AptAptmtRepport AS a WHERE a.id > ?1 ORDER BY a.id ASC ")
	public QueryResult<AptAptmtRepport> findNextAptAptmtRepport(String id);

	@Query("SELECT a from AptAptmtRepport AS a WHERE a.id < ?1 ORDER BY a.id ASC")
	public QueryResult<AptAptmtRepport> findPreviousAptAptmtRepport(String id);

}
