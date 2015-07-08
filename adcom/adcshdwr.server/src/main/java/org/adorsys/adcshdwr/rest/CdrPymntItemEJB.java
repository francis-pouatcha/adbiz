package org.adorsys.adcshdwr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adcshdwr.jpa.CdrPymntItem;
import org.adorsys.adcshdwr.jpa.CdrPymntItemEvt;
import org.adorsys.adcshdwr.repo.CdrPymntItemRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CdrPymntItemEJB
{

	@Inject
	private CdrPymntItemRepository repository;

	@Inject
	private CdrPymntItemEvtEJB pymntItemEvtEJB;
	
	public CdrPymntItem create(CdrPymntItem entity)
	{
		if (StringUtils.isBlank(entity.getPymntDocNbr())) {
			entity.setPymntDocNbr(SequenceGenerator
					.getSequence(SequenceGenerator.PAYMENT_SEQUENCE_PREFIX));
		}
		entity.setPymntDt(new Date());
		entity = repository.save(attach(entity));
		CdrPymntItemEvt pymntItemEvt = new CdrPymntItemEvt();
		entity.copyTo(pymntItemEvt);
		pymntItemEvtEJB.create(pymntItemEvt);
		return entity;
	}

	public CdrPymntItem deleteById(String id)
	{
		CdrPymntItem entity = repository.findBy(id);
		if (entity != null)
		{
			CdrPymntItemEvt pymntItemEvt = pymntItemEvtEJB.findById(entity.getId());
			if(pymntItemEvt != null) pymntItemEvtEJB.deleteById(id);
			repository.remove(entity);
		}
		return entity;
	}

	public CdrPymntItem update(CdrPymntItem entity)
	{
		entity = repository.save(attach(entity));
		CdrPymntItemEvt pymntItemEvt = pymntItemEvtEJB.findById(entity.getId());
		if(pymntItemEvt != null) {
			pymntItemEvt.copyTo(entity);
			pymntItemEvtEJB.update(pymntItemEvt);
		}
		return entity;
	}

	public CdrPymntItem findById(String id)
	{
		return repository.findBy(id);
	}

	public List<CdrPymntItem> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<CdrPymntItem> findBy(CdrPymntItem entity, int start, int max, SingularAttribute<CdrPymntItem, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(CdrPymntItem entity, SingularAttribute<CdrPymntItem, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<CdrPymntItem> findByLike(CdrPymntItem entity, int start, int max, SingularAttribute<CdrPymntItem, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(CdrPymntItem entity, SingularAttribute<CdrPymntItem, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	private CdrPymntItem attach(CdrPymntItem entity)
	{
		if (entity == null)
			return null;

		return entity;
	}

	/**
	 * countByPymntNbr.
	 *
	 * @param pymntNbr
	 * @return
	 */
	public Long countByPymntNbr(String pymntNbr) {
		return repository.findByPymntNbr(pymntNbr).count();
	}

	/**
	 * findByPymntNbr.
	 *
	 * @param pymntNbr
	 * @param start
	 * @param max
	 * @return
	 */
	public List<CdrPymntItem> findByPymntNbr(String pymntNbr, int start, int max) {
		return repository.findByPymntNbr(pymntNbr).firstResult(start).maxResults(max).getResultList();
	}
	
	public List<CdrPymntItem> findByPymntNbr(String pymntNbr) {
		return repository.findByPymntNbr(pymntNbr).getResultList();
	}
}
