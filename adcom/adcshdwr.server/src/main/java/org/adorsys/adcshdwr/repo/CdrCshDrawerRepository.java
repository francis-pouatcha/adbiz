package org.adorsys.adcshdwr.repo;

import java.util.List;

import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrCshDrawer.class)
public interface CdrCshDrawerRepository extends EntityRepository<CdrCshDrawer, String>
{
	@Query("SELECT e FROM CdrCshDrawer AS e WHERE e.cashier = ?1 AND e.opngDt IS NOT NULL AND e.clsngDt IS NULL")
	public List<CdrCshDrawer> findOpenedCshDrawerByCashier(String cashier);
	
	@Query("SELECT e FROM CdrCshDrawer AS e WHERE e.cashier = ?1 AND e.opngDt IS NOT NULL AND e.clsngDt IS NULL ORDER BY e.clsngDt DESC")
	public List<CdrCshDrawer> findPrevious(String cashier);
}
