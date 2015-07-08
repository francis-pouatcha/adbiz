package org.adorsys.adaptmt.repo;

import org.adorsys.adaptmt.jpa.AptAptmtBsPtnr;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = AptAptmtBsPtnr.class)
public interface AptAptmtBsPtnrRepository extends
		EntityRepository<AptAptmtBsPtnr, String> {

	@Query("SELECT b from AptAptmtBsPtnr AS b WHERE b.aptmtIdentify = ?1")
	public QueryResult<AptAptmtBsPtnr> findAptAptmtBsPtnr(String aptmtIdentify);

}
