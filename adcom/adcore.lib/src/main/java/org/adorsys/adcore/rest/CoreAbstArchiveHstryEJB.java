package org.adorsys.adcore.rest;

import java.util.List;

import org.adorsys.adcore.jpa.CoreAbstIdentifHstry;
import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;

public abstract class CoreAbstArchiveHstryEJB<H extends CoreAbstIdentifHstry> {

	protected abstract CoreAbstIdentifObjectHstryRepo<H> getRepo();

	public H create(H entity) {
		H saved = getRepo().save(entity);
		return saved;
	}

	public H deleteById(String id) {
		H entity = getRepo().findBy(id);
		if (entity != null) {
			getRepo().remove(entity);
		}
		return entity;
	}
	
	public void deleteByEntIdentif(String invtryNbr, int first, int max) {
		List<H> list = getRepo().findByEntIdentif(invtryNbr).firstResult(first).maxResults(max).getResultList();
		for (H hstry : list) {
			getRepo().remove(hstry);
		}
	}

	public H update(H entity) {
		return getRepo().save(attach(entity));
	}

	private H attach(H entity) {
		if (entity == null)
			return null;
		return entity;
	}
}
