package org.adorsys.adcore.loader.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.loader.jpa.CorLdrJobCstr;
import org.adorsys.adcore.loader.repo.CorLdrJobCstrRepo;
import org.adorsys.adcore.repo.CoreAbstEntityCstrRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityCstrEJB;

@Stateless
public class CorLdrJobCstrEJB extends CoreAbstEntityCstrEJB<CorLdrJobCstr> {

	@Inject
	private CorLdrJobCstrRepo repo;

	@Override
	public CorLdrJobCstr newCsrInstance() {
		return new CorLdrJobCstr();
	}

	@Override
	protected CoreAbstIdentifRepo<CorLdrJobCstr> getRepo() {
		return repo;
	}
	protected CoreAbstEntityCstrRepo<CorLdrJobCstr> getCstrRepo(){
		return repo;
	}
}
