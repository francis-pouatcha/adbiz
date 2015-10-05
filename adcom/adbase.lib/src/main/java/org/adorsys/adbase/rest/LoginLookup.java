package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.repo.LoginRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class LoginLookup extends CoreAbstIdentifLookup<Login> {

	@Inject
	private LoginRepository repository;

	@Override
	protected CoreAbstIdentifRepo<Login> getRepo() {
		return repository;
	}

	@Override
	protected Class<Login> getEntityClass() {
		return Login.class;
	}
}
