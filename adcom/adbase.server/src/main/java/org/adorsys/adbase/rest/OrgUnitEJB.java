package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.jpa.OuType;
import org.adorsys.adbase.repo.OrgUnitRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.apache.commons.lang3.RandomStringUtils;

@Stateless
public class OrgUnitEJB extends CoreAbstIdentifiedEJB<OrgUnit> {
	@Inject
	private OrgUnitRepository repository;

	@Inject
	private OuTypeLookup ouTypeLookup;

	public OrgUnit create(OrgUnit entity) {
		String typeIdentif = entity.getTypeIdentif();
		OuType ouType = ouTypeLookup.findByIdentif(typeIdentif);
		Integer idSize = ouType.getIdSize();
		String generatedId = RandomStringUtils.randomAlphanumeric(idSize)
				.toUpperCase();
		generatedId = entity.getCntnrIdentif().concat(generatedId);
		entity.setIdentif(generatedId);
		return repository.save(attach(entity));
	}

	@Override
	protected CoreAbstIdentifRepo<OrgUnit> getRepo() {
		return repository;
	}
}
