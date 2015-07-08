package org.adorsys.adsales.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adsales.jpa.SlsInvoice;
import org.adorsys.adsales.repo.SlsInvoiceRepository;

@Stateless
public class SlsInvoiceLookup
{

	@Inject
	private SlsInvoiceRepository repository;

	public SlsInvoice findById(String id)
	{
		return repository.findBy(id);
	}

	public List<SlsInvoice> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<SlsInvoice> findBy(SlsInvoice entity, int start, int max, SingularAttribute<SlsInvoice, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(SlsInvoice entity, SingularAttribute<SlsInvoice, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<SlsInvoice> findByLike(SlsInvoice entity, int start, int max, SingularAttribute<SlsInvoice, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(SlsInvoice entity, SingularAttribute<SlsInvoice, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}
}
