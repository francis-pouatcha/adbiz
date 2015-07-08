package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEvtRepo;
import org.adorsys.adcore.rest.CoreAbstEvtEJB;
import org.adorsys.adinvtry.jpa.InvInvtryEvt;
import org.adorsys.adinvtry.repo.InvInvtryEvtRepository;

@Stateless
public class InvInvtryEvtEJB extends CoreAbstEvtEJB<InvInvtryEvt> {

	@Inject
	private InvInvtryEvtRepository repository;

	@Override
	protected CoreAbstEvtRepo<InvInvtryEvt> getRepo() {
		return repository;
	}

}
