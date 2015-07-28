package org.adorsys.adsales.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adsales.jpa.SlsInvceEvtLease;
import org.adorsys.adsales.repo.SlsInvceEvtLeaseRepository;

@Stateless
public class SlsInvceEvtLeaseEJB {

	@Inject
	private SlsInvceEvtLeaseRepository repository;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public SlsInvceEvtLease create(SlsInvceEvtLease entity) {
		return repository.save(attach(entity));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String recover(String processOwner, String leaseId) {
		SlsInvceEvtLease lease = findById(leaseId);
		if(lease==null) return null;
		lease.extend(processOwner);
		return repository.save(attach(lease)).getId();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String close(String processOwner, String leaseId) {
		SlsInvceEvtLease lease = findById(leaseId);
		if(lease==null) return null;
		if(lease.expired(new Date())) return null;
		lease.setValidTo(new Date());;
		return repository.save(attach(lease)).getId();
	}
	
	public SlsInvceEvtLease deleteById(String id) {
		SlsInvceEvtLease entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public SlsInvceEvtLease update(SlsInvceEvtLease entity) {
		return repository.save(attach(entity));
	}

	public SlsInvceEvtLease findById(String id) {
		return repository.findBy(id);
	}

	public List<SlsInvceEvtLease> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<SlsInvceEvtLease> findBy(SlsInvceEvtLease entity,
			int start, int max,
			SingularAttribute<SlsInvceEvtLease, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(SlsInvceEvtLease entity,
			SingularAttribute<SlsInvceEvtLease, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<SlsInvceEvtLease> findByLike(SlsInvceEvtLease entity,
			int start, int max,
			SingularAttribute<SlsInvceEvtLease, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(SlsInvceEvtLease entity,
			SingularAttribute<SlsInvceEvtLease, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private SlsInvceEvtLease attach(SlsInvceEvtLease entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public List<SlsInvceEvtLease> findByEvtId(String evtId) {
		return repository.findByEvtId(evtId);
	}
	
	public List<SlsInvceEvtLease> findByEvtIdAndHandlerName(String evtId, String handlerName){
		return repository.findByEvtIdAndHandlerName(evtId, handlerName);
	}
}
