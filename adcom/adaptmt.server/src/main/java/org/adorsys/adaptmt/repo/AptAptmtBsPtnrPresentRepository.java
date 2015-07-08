package org.adorsys.adaptmt.repo;

import org.adorsys.adaptmt.jpa.AptAptmtBsPtnr;
import org.adorsys.adaptmt.jpa.AptAptmtBsPtnrPresent;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = AptAptmtBsPtnrPresent.class)
public interface AptAptmtBsPtnrPresentRepository extends
		EntityRepository<AptAptmtBsPtnrPresent, String> {

	@Query("SELECT b from AptAptmtBsPtnrPresent AS b WHERE b.aptmtIdentify = ?1")
	public QueryResult<AptAptmtBsPtnrPresent> findAptAptmtBsPtnrPresent(String aptmtIdentify);

}
