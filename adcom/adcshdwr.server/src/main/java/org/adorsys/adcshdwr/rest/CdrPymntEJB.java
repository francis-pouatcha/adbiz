package org.adorsys.adcshdwr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adcshdwr.jpa.CdrPymnt;
import org.adorsys.adcshdwr.jpa.CdrPymntEvt;
import org.adorsys.adcshdwr.repo.CdrPymntRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CdrPymntEJB
{

	@Inject
	private CdrPymntRepository repository;

	@Inject
	private CdrPymntEvtEJB pymntEvtEJB;
	@Inject
	private SecurityUtil securityUtil;
	
	public CdrPymnt create(CdrPymnt entity)
	{
		//entity.setCdrNbr(SequenceGenerator.getSequence(SequenceGenerator.PAYMENT_SEQUENCE_PREFIX));
		if (StringUtils.isBlank(entity.getPymntNbr())) {
			entity.setPymntNbr(SequenceGenerator
					.getSequence(SequenceGenerator.PAYMENT_SEQUENCE_PREFIX));
		}
		entity.setCashier(securityUtil.getCurrentLoginName());
		Date pymntDt = new Date();
		entity.setValueDt(pymntDt);//TODO : is it the pymnt date ?
		if(StringUtils.isBlank(entity.getPaidBy())) {
			entity.setPaidBy("--Auto--");//maybe a direct sales
		}
		if(StringUtils.isBlank(entity.getRcptNbr())) {
			entity.setRcptNbr("--Auto--");//maybe a direct sales
			entity.setRcptPrntDt(pymntDt);
		}
		entity = repository.save(attach(entity));
		CdrPymntEvt pymntEvt = new CdrPymntEvt();
		entity.copyTo(pymntEvt);
		pymntEvtEJB.create(pymntEvt);
		return entity;
	}

	public CdrPymnt deleteById(String id)
	{
		CdrPymnt entity = repository.findBy(id);
		if (entity != null)
		{
			CdrPymntEvt pymntEvt = pymntEvtEJB.findById(entity.getId());
			if(pymntEvt != null) pymntEvtEJB.deleteById(id);
			repository.remove(entity);
		}
		return entity;
	}

	public CdrPymnt update(CdrPymnt entity)
	{
		entity = repository.save(attach(entity));
		CdrPymntEvt pymntEvt = pymntEvtEJB.findById(entity.getId());
		if(pymntEvt != null) {
			entity.copyTo(pymntEvt);
			pymntEvtEJB.update(pymntEvt);
		}
		return entity;
	}

	public CdrPymnt findById(String id)
	{
		return repository.findBy(id);
	}

	public List<CdrPymnt> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<CdrPymnt> findBy(CdrPymnt entity, int start, int max, SingularAttribute<CdrPymnt, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(CdrPymnt entity, SingularAttribute<CdrPymnt, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<CdrPymnt> findByLike(CdrPymnt entity, int start, int max, SingularAttribute<CdrPymnt, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(CdrPymnt entity, SingularAttribute<CdrPymnt, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	private CdrPymnt attach(CdrPymnt entity)
	{
		if (entity == null)
			return null;

		return entity;
	}
	
	public List<CdrPymnt> findByPymntNbr(String pymntNbr){
		return repository.findByPymntNbr(pymntNbr);
	}
	
	public List<CdrPymnt> findByInvNbr(String invNbr){
		return repository.findByInvNbr(invNbr);
	}
}
