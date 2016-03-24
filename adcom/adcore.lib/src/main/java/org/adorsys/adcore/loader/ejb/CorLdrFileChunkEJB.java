package org.adorsys.adcore.loader.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.loader.jpa.CorLdrFileChunk;
import org.adorsys.adcore.loader.jpa.CorLdrFileStream;
import org.adorsys.adcore.loader.repo.CorLdrFileChunkRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class CorLdrFileChunkEJB extends CoreAbstIdentifiedEJB<CorLdrFileChunk> {

	@Inject
	private CorLdrFileChunkRepo repo;

	@Override
	protected CoreAbstIdentifRepo<CorLdrFileChunk> getRepo() {
		return repo;
	}
	
	public void handleDeletedEvent(@Observes @EntityDeletedEvent CorLdrFileStream stream){
		long count = repo.findByCntnrIdentif(stream.getIdentif()).count();
		int deletedCount = 0;
		int max = 5;
		while(deletedCount<count){
			int start = deletedCount;
			deletedCount +=max;
			List<CorLdrFileChunk> resultList = repo.findByCntnrIdentif(stream.getIdentif()).firstResult(start).maxResults(max).getResultList();
			for (CorLdrFileChunk corLdrFileChunk : resultList) {
				deleteById(corLdrFileChunk.getId());
			}
		}
	}

}
