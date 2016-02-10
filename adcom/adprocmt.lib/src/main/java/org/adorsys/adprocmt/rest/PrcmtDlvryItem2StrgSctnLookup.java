package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctn;
import org.adorsys.adprocmt.repo.PrcmtDlvryItem2StrgSctnRepository;

@Stateless
public class PrcmtDlvryItem2StrgSctnLookup extends CoreAbstIdentifLookup<PrcmtDlvryItem2StrgSctn>{

	@Inject
	private PrcmtDlvryItem2StrgSctnRepository repo;
	
	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvryItem2StrgSctn> getRepo() {
		return repo;
	}

	@Override
	protected Class<PrcmtDlvryItem2StrgSctn> getEntityClass() {
		return PrcmtDlvryItem2StrgSctn.class;
	}

	public PrcmtDlvryItem2StrgSctn findByCntnrIdentifAndStrgSection(String cntnrIdentif, String strgSection) {
		return repo.findByCntnrIdentifAndStrgSection(cntnrIdentif, strgSection).getOptionalResult();
	}

}
