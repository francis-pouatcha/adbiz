package org.adorsys.adcshdwr.rest;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adcshdwr.jpa.CdrPymntItem;
import org.adorsys.adcshdwr.repo.CdrPymntItemRepo;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CdrPymntItemEJB extends CoreAbstIdentifiedEJB<CdrPymntItem>{

	@Inject
	private CdrPymntItemRepo repository;

	public CdrPymntItem create(CdrPymntItem entity)
	{
		if (StringUtils.isBlank(entity.getPymntDocNbr())) {
			entity.setPymntDocNbr(SequenceGenerator
					.getSequence(SequenceGenerator.PAYMENT_SEQUENCE_PREFIX));
		}
		entity.setPymntDt(new Date());

		return super.create(entity);
	}

	@Override
	protected CoreAbstIdentifRepo<CdrPymntItem> getRepo() {
		return repository;
	}

}
