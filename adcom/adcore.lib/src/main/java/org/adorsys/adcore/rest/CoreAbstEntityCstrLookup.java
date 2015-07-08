package org.adorsys.adcore.rest;

import java.util.List;

import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.repo.CoreAbstEntityCstrRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;

public abstract class CoreAbstEntityCstrLookup<E extends CoreAbstEntityCstr> extends CoreAbstIdentifiedLookup<E>{
	protected abstract CoreAbstEntityCstrRepo<E> getCstrRepo();

	protected CoreAbstIdentifDataRepo<E> getRepo() {
		return getCstrRepo();
	}

	public Long countByEntIdentif(String entIdentif){
		return getCstrRepo().findByEntIdentif(entIdentif).count();
	}
	public List<E> findByEntIdentif(String entIdentif, int start, int max){
		return getCstrRepo().findByEntIdentif(entIdentif).firstResult(start).maxResults(max).getResultList();
	}

	public Long countByEntIdentifAndCstrType(String entIdentif, String cstrType){
		return getCstrRepo().findByEntIdentifAndCstrType(entIdentif, cstrType).count();
	}
	public List<E> findByEntIdentifAndCstrType(String entIdentif, String cstrType, int start, int max){
		return getCstrRepo().findByEntIdentifAndCstrType(entIdentif, cstrType).firstResult(start).maxResults(max).getResultList();		
	}

	public Long countByCstrType(String cstrType){
		return getCstrRepo().findByCstrType(cstrType).count();
	}
	public List<E> findByCstrType(String cstrType, int start, int max){
		return getCstrRepo().findByCstrType(cstrType).firstResult(start).maxResults(max).getResultList();		
	}
}
