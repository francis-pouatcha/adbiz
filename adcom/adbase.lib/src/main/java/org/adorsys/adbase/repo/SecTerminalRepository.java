package org.adorsys.adbase.repo;

import java.util.Date;

import org.adorsys.adbase.jpa.SecTerminal;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SecTerminal.class)
public interface SecTerminalRepository extends EntityRepository<SecTerminal, String>
{
	@Query("SELECT e FROM SecTerminal AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<SecTerminal> findByIdentif(String identif, Date validOn);
	
	@Query("SELECT e FROM SecTerminal AS e WHERE e.validFrom <= ?1 AND (e.validTo IS NULL OR e.validTo > ?1)")
	public abstract QueryResult<SecTerminal> findActiveSecTerminal(Date currentDate);
	
	@Query("SELECT COUNT(e) FROM SecTerminal AS e WHERE e.validFrom <= ?1 AND (e.validTo IS NULL OR e.validTo > ?1) ")
	public abstract Long countActiveSecTerminal(Date currentDate);
}
