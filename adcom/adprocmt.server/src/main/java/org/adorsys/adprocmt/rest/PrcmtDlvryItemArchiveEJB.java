package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemArchive;
import org.adorsys.adprocmt.repo.PrcmtDlvryItemArchiveRepository;

/**
 * Stores a delivery item. 
 * 
 * Whenever a delivery item is stored, a copy (event data) is also stored for event processing.
 * This copy is updated and deleted synchronously with the delivery item.
 * 
 * The event data object can also be deleted under certain condition independently of the delivery item object.
 * 
 * @author francis
 *
 */
@Stateless
public class PrcmtDlvryItemArchiveEJB extends CoreAbstIdentifiedEJB<PrcmtDlvryItemArchive>{
	@Inject
	private PrcmtDlvryItemArchiveRepository repo;

	@Override
	protected CoreAbstIdentifRepo<PrcmtDlvryItemArchive> getRepo() {
		return repo;
	}


}
