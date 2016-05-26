package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OrgContact;
import org.adorsys.adbase.jpa.OrgContactRole;
import org.adorsys.adbase.repo.OrgContactRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class OrgContactLookup extends CoreAbstIdentifLookup<OrgContact> {

	@Inject
	private OrgContactRepository repository;

	@Override
	protected CoreAbstIdentifRepo<OrgContact> getRepo() {
		return repository;
	}

	@Override
	protected Class<OrgContact> getEntityClass() {
		return OrgContact.class;
	}
	
	public OrgContact findFirstMainContact(String ouIdentif){
		String identif = OrgContact.makeIdentif(ouIdentif, OrgContactRole.MAIN_CONTACT.name(), "a0");
		return findByIdentif(identif);
	}
	
}
