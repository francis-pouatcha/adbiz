package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemArchive;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemArchive2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemArchive2POItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemArchive2StrgSctn;
import org.adorsys.adprocmt.repo.PrcmtDlvryItemArchive2OuRepository;
import org.adorsys.adprocmt.repo.PrcmtDlvryItemArchive2POItemRepository;
import org.adorsys.adprocmt.repo.PrcmtDlvryItemArchive2StrgSctnRepository;
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
public class PrcmtDlvryItemArchiveLookup extends CoreAbstBsnsItemLookup<PrcmtDlvryItemArchive>{

	@Inject
	private PrcmtDlvryItemArchiveRepository repository;

	@Inject
	private PrcmtDlvryItemArchive2OuRepository ouRepository;
	
	@Inject
	private PrcmtDlvryItemArchive2POItemRepository poItemRepository;
	
	@Inject
	private PrcmtDlvryItemArchive2StrgSctnRepository strgSctnRepository;

	@Override
	protected CoreAbstBsnsItemRepo<PrcmtDlvryItemArchive> getBsnsRepo() {
		return repository;
	}

	public PrcmtDlvryItemArchive2Ou findDlvryItem2Ou(String dlvryItemNbr, String orgUnit){
		return ouRepository.findBy(PrcmtDlvryItemArchive2Ou.toId(dlvryItemNbr, orgUnit));
	}
	public PrcmtDlvryItemArchive2POItem findDlvryItem2POItem(String dlvryItemNbr, String poItemNbr){
		return poItemRepository.findBy(PrcmtDlvryItemArchive2POItem.toId(dlvryItemNbr, poItemNbr));
	}
	public PrcmtDlvryItemArchive2StrgSctn findDlvryItem2StrgSctn(String dlvryItemNbr, String strgSctn){
		return strgSctnRepository.findBy(PrcmtDlvryItemArchive2StrgSctn.toId(dlvryItemNbr, strgSctn));
	}

	public Long countDlvryItem2Ou(String dlvryItemNbr){
		return ouRepository.findByCntnrIdentif(dlvryItemNbr).count();
	}
	public Long countDlvryItem2POItem(String dlvryItemNbr){
		return poItemRepository.findByCntnrIdentif(dlvryItemNbr).count();
	}
	public Long countDlvryItem2StrgSctn(String dlvryItemNbr){
		return strgSctnRepository.findByCntnrIdentif(dlvryItemNbr).count();
	}

	public List<PrcmtDlvryItemArchive2Ou> listDlvryItem2Ou(String dlvryItemNbr, int start, int max){
		return ouRepository.findByCntnrIdentif(dlvryItemNbr).firstResult(start).maxResults(max).getResultList();
	}
	public List<PrcmtDlvryItemArchive2POItem> listDlvryItem2POItem(String dlvryItemNbr, int start, int max){
		return poItemRepository.findByCntnrIdentif(dlvryItemNbr).firstResult(start).maxResults(max).getResultList();
	}
	public List<PrcmtDlvryItemArchive2StrgSctn> listDlvryItem2StrgSctn(String dlvryItemNbr, int start, int max){
		return strgSctnRepository.findByCntnrIdentif(dlvryItemNbr).firstResult(start).maxResults(max).getResultList();
	}
	
	@Override
	protected Class<PrcmtDlvryItemArchive> getEntityClass() {
		return PrcmtDlvryItemArchive.class;
	}

}
