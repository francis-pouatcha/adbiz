package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OrgContact;
import org.adorsys.adbase.repo.OrgContactRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class OrgContactEJB extends CoreAbstIdentifiedEJB<OrgContact> {

	@Inject
	private OrgContactRepository repository;

	@Override
	protected CoreAbstIdentifRepo<OrgContact> getRepo() {
		return repository;
	}
}
