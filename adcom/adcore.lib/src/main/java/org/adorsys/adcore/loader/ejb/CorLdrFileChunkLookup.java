package org.adorsys.adcore.loader.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.loader.jpa.CorLdrFileChunk;
import org.adorsys.adcore.loader.repo.CorLdrFileChunkRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class CorLdrFileChunkLookup extends
		CoreAbstIdentifLookup<CorLdrFileChunk> {

	@Inject
	private CorLdrFileChunkRepo repo;

	@Override
	protected Class<CorLdrFileChunk> getEntityClass() {
		return CorLdrFileChunk.class;
	}

	@Override
	protected CoreAbstIdentifRepo<CorLdrFileChunk> getRepo() {
		return repo;
	}

	public List<CorLdrFileChunk> findByCntnrIdentifOderPosition(String cntnrIdentif, int start, int max) {
		return repo.findByCntnrIdentif(cntnrIdentif).orderAsc("position").firstResult(start).maxResults(max).getResultList();
	}
}
