package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.repo.StkSectionRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class StkSectionEJB extends CoreAbstIdentifiedEJB<StkSection> {

	@Inject
	private StkSectionRepository repository;

	@Override
	protected CoreAbstIdentifRepo<StkSection> getRepo() {
		return repository;
	}

	/**
	 * while creating, first look for the container, and construct the path.
	 */
	@Override
	public StkSection create(StkSection entity) {
		String cntnrIdentif = entity.getCntnrIdentif();
		if(StringUtils.isNotBlank(cntnrIdentif)){
			StkSection container = repository.findByIdentif(cntnrIdentif).getOptionalResult();
			if(container==null) throw new IllegalStateException("Sepecified container not found for section : " + entity.getIdentif());
			String parentPathPrefixed = container.getPath()==null?"":"_" + container.getPath();
			entity.setPath(container.getIdentif() + parentPathPrefixed);
		}
		return super.create(entity);
	}
	
	
}
