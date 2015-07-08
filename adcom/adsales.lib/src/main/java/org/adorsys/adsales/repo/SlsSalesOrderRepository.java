package org.adorsys.adsales.repo;


import java.util.Date;

import org.adorsys.adsales.jpa.SlsSalesOrder;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsSalesOrder.class)
public interface SlsSalesOrderRepository extends EntityRepository<SlsSalesOrder, String>{
	
	@Query("SELECT s FROM SlsSalesOrder WHERE s.acsngUser = ?1 AND s.soNbr = ?2 AND s.soDt BETWEEN ?3 AND ?4")
	public QueryResult<SlsSalesOrder> findSlsSalesOrderByAcsngUserAndSoNbrAndDateBtw(String acsngUser, String soNbr, Date from, Date to);
	
}
