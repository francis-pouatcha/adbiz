package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.jpa.PrcmtPOItem2StrgSctn;
import org.adorsys.adprocmt.repo.PrcmtPOItem2StrgSctnRepository;
import org.adorsys.adprocmt.repo.PrcmtPOItemRepository;

@Stateless
public class PrcmtPOItemLookup extends CoreAbstBsnsItemLookup<PrcmtPOItem> {

	@Inject
	private PrcmtPOItemRepository repository;

	@Inject
	private PrcmtPOItem2StrgSctnRepository strgSctnRepository;
	
	@Override
	protected CoreAbstBsnsItemRepo<PrcmtPOItem> getBsnsRepo() {
		return repository;
	}

	@Override
	protected Class<PrcmtPOItem> getEntityClass() {
		return PrcmtPOItem.class;
	}

	public PrcmtPOItem2StrgSctn findPOItem2StrgSctn(String poItemNbr, String strgSctn){
		return strgSctnRepository.findBy(PrcmtPOItem2StrgSctn.toId(poItemNbr, strgSctn));
	}
	
	public Long countPOItem2StrgSctn(String poItemNbr){
		return strgSctnRepository.findByCntnrIdentif(poItemNbr).count();
	}

	public List<PrcmtPOItem2StrgSctn> listPOItem2StrgSctn(String poItemNbr, int start, int max){
		return strgSctnRepository.findByCntnrIdentif(poItemNbr).firstResult(start).maxResults(max).getResultList();
	}
	
}
