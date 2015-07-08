package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2POItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctn;
import org.adorsys.adprocmt.repo.PrcmtDlvryItem2OuRepository;
import org.adorsys.adprocmt.repo.PrcmtDlvryItem2POItemRepository;
import org.adorsys.adprocmt.repo.PrcmtDlvryItem2StrgSctnRepository;
import org.adorsys.adprocmt.repo.PrcmtDlvryItemRepository;

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
public class PrcmtDlvryItemLookup extends CoreAbstBsnsItemLookup<PrcmtDlvryItem>{

	@Inject
	private PrcmtDlvryItemRepository repository;

	@Inject
	private PrcmtDlvryItem2OuRepository ouRepository;
	
	@Inject
	private PrcmtDlvryItem2POItemRepository poItemRepository;
	
	@Inject
	private PrcmtDlvryItem2StrgSctnRepository strgSctnRepository;
	
	@Inject
	private EntityManager em;

	@Override
	protected CoreAbstBsnsItemRepo<PrcmtDlvryItem> getBsnsRepo() {
		return repository;
	}

	public PrcmtDlvryItem2Ou findDlvryItem2Ou(String dlvryItemNbr, String orgUnit){
		return ouRepository.findBy(PrcmtDlvryItem2Ou.toId(dlvryItemNbr, orgUnit));
	}
	public PrcmtDlvryItem2POItem findDlvryItem2POItem(String dlvryItemNbr, String poItemNbr){
		return poItemRepository.findBy(PrcmtDlvryItem2POItem.toId(dlvryItemNbr, poItemNbr));
	}
	public PrcmtDlvryItem2StrgSctn findDlvryItem2StrgSctn(String dlvryItemNbr, String strgSctn){
		return strgSctnRepository.findBy(PrcmtDlvryItem2StrgSctn.toId(dlvryItemNbr, strgSctn));
	}

	public List<PrcmtDlvryItem2Ou> listDlvryItem2Ou(String dlvryItemNbr){
		return ouRepository.findByDlvryItemNbr(dlvryItemNbr);
	}
	public List<PrcmtDlvryItem2POItem> listDlvryItem2POItem(String dlvryItemNbr){
		return poItemRepository.findByDlvryItemNbr(dlvryItemNbr);
	}
	public List<PrcmtDlvryItem2StrgSctn> listDlvryItem2StrgSctn(String dlvryItemNbr){
		return strgSctnRepository.findByDlvryItemNbr(dlvryItemNbr);
	}

	@Override
	protected Class<PrcmtDlvryItem> getBsnsObjClass() {
		return PrcmtDlvryItem.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
