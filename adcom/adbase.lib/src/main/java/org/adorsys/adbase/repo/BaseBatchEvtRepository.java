package org.adorsys.adbase.repo;

import org.adorsys.adbase.jpa.BaseBatchEvt;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BaseBatchEvt.class)
public interface BaseBatchEvtRepository extends EntityRepository<BaseBatchEvt, String>{
	
	@Query("SELECT e FROM BaseBatchEvt AS e WHERE e.evtModule=?1 AND e.evtKlass=?2 AND e.evtName=?3")
	public QueryResult<BaseBatchEvt> findByEvtModuleAndEvtKlassAndEvtName(String evtModule, String evtKlass, String evtName);
	
	@Query("SELECT e FROM BaseBatchEvt AS e WHERE e.entIdentif=?1 AND e.evtModule=?2 AND e.evtKlass=?3 AND e.evtName=?4")
	public QueryResult<BaseBatchEvt> findByEntIdentifAndEvtModuleAndEvtKlassAndEvtName(String entIdentif, String evtModule, String evtKlass, String evtName);
	
}
