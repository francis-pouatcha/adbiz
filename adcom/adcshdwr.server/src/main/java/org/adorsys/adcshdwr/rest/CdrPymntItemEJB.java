package org.adorsys.adcshdwr.rest;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.adorsys.adcshdwr.jpa.CdrPymnt;
import org.adorsys.adcshdwr.jpa.CdrPymntItem;
import org.adorsys.adcshdwr.jpa.CdrPymntMode;
import org.adorsys.adcshdwr.repo.CdrPymntItemRepo;
import org.apache.commons.lang3.StringUtils;


@Stateless
public class CdrPymntItemEJB extends CoreAbstIdentifiedEJB<CdrPymntItem>{

	@Inject
	private CdrPymntItemRepo repository;
	@Inject
	private CdrCshDrawerEJB cdrCshDrawerEJB;
	@Inject
	private CdrPymntEJB cdrPymntEJB;

	@Transactional
	public CdrPymntItem create(CdrPymntItem entity)
	{
		if (StringUtils.isBlank(entity.getPymntDocNbr())) {
			entity.setPymntDocNbr(SequenceGenerator
					.getSequence(SequenceGenerator.PAYMENT_SEQUENCE_PREFIX));
			entity.setIdentif(entity.getPymntDocNbr());
		}
		entity.setPymntDt(new Date());
		CdrPymntItem pymntItem = super.create(entity);		
		updateCshDrawer(pymntItem);	
		return pymntItem;
	}

	private void updateCshDrawer(CdrPymntItem pymntItem) {
		CdrPymnt cdrPymnt = cdrPymntEJB.findByIdentif(pymntItem.getCntnrIdentif());
		CdrCshDrawer cshDrawer = cdrCshDrawerEJB.findByIdentif(cdrPymnt.getCdrNbr());
		if(pymntItem.getPymntMode()==CdrPymntMode.CASH){
			cshDrawer.setTtlCash(cshDrawer.getTtlCash().add(pymntItem.getAmt()));
		}
		if(pymntItem.getPymntMode()==CdrPymntMode.CHECK){
			cshDrawer.setTtlCheck(cshDrawer.getTtlCheck().add(pymntItem.getAmt()));
		}
		if(pymntItem.getPymntMode()==CdrPymntMode.CREDIT_CARD){
			cshDrawer.setTtlCredCard(cshDrawer.getTtlCredCard().add(pymntItem.getAmt()));
		}
		cshDrawer.setTtlCashIn(cshDrawer.getTtlCash().add(cshDrawer.getTtlCheck()).add(cshDrawer.getTtlCredCard()).add(cshDrawer.getInitialAmt()));
		cdrCshDrawerEJB.update(cshDrawer);
	}

	@Override
	protected CoreAbstIdentifRepo<CdrPymntItem> getRepo() {
		return repository;
	}

}
