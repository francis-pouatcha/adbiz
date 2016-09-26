package org.adorsys.adcshdwr.rest;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.auth.AdcomUserFactory;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adcshdwr.jpa.CdrPymnt;
import org.adorsys.adcshdwr.repo.CdrPymntRepo;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CdrPymntEJB extends CoreAbstIdentifiedEJB<CdrPymnt>{

	@Inject
	private CdrPymntRepo repo;
	@Resource
	private SessionContext context;
	
	@Override
	public CdrPymnt create(CdrPymnt entity)
	{
		//entity.setCdrNbr(SequenceGenerator.getSequence(SequenceGenerator.PAYMENT_SEQUENCE_PREFIX));
		if (StringUtils.isBlank(entity.getIdentif())) {
			entity.setIdentif(SequenceGenerator
					.getSequence(SequenceGenerator.PAYMENT_SEQUENCE_PREFIX));
		}
		entity.setCashier(AdcomUserFactory.getAdcomUser(context).getLoginName());
		Date pymntDt = new Date();
		entity.setValueDt(pymntDt);
		if(StringUtils.isBlank(entity.getPaidBy())) {
			entity.setPaidBy("--Auto--");//maybe a direct sales
		}
		if(StringUtils.isBlank(entity.getRcptNbr())) {
			entity.setRcptNbr("--Auto--");//maybe a direct sales
			entity.setRcptPrntDt(pymntDt);
		}
		return super.create(entity);
	}

	@Override
	protected CoreAbstIdentifRepo<CdrPymnt> getRepo() {
		return repo;
	}
}
