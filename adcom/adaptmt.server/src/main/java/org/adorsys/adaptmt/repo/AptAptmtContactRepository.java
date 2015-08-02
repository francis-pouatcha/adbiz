package org.adorsys.adaptmt.repo;

import org.adorsys.adaptmt.jpa.AptAptmtContact;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = AptAptmtContact.class)
public interface AptAptmtContactRepository extends
		EntityRepository<AptAptmtContact, String> {
	
	@Query("SELECT a from AptAptmtContact AS a WHERE a.id > ?1 ORDER BY a.id ASC ")
	public QueryResult<AptAptmtContact> findNextAptAptmtContact(String id);

	@Query("SELECT a from AptAptmtContact AS a WHERE a.id < ?1 ORDER BY a.id ASC")
	public QueryResult<AptAptmtContact> findPreviousAptAptmtContact(String id);
	
}
