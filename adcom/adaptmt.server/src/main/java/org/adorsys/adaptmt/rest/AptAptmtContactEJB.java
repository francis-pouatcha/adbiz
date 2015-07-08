package org.adorsys.adaptmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adaptmt.jpa.AptAptmtContact;
import org.adorsys.adaptmt.repo.AptAptmtContactRepository;
import org.adorsys.adcore.utils.SequenceGenerator;


@Stateless
public class AptAptmtContactEJB 
{

	   @Inject
	   private AptAptmtContactRepository repository;

	   public AptAptmtContact create(AptAptmtContact entity)
	   {
		   entity.setIdentif(SequenceGenerator
					.getSequence(SequenceGenerator.APPOINTMENTCONTACT_NUMBER_SEQUENCE_PREFIXE));
		   
	      return repository.save(attach(entity));
	   }

	   public AptAptmtContact deleteById(String id)
	   {
		   AptAptmtContact entity = repository.findBy(id);
	      if (entity != null)
	      {
	         repository.remove(entity);
	      }
	      return entity;
	   }

	   public AptAptmtContact update(AptAptmtContact entity)
	   {
	      return repository.save(attach(entity));
	   }

	   public AptAptmtContact findById(String id)
	   {
	      return repository.findBy(id);
	   }

	   public List<AptAptmtContact> listAll(int start, int max)
	   {
	      return repository.findAll(start, max);
	   }

	   public Long count()
	   {
	      return repository.count();
	   }

	   public List<AptAptmtContact> findBy(AptAptmtContact entity, int start, int max, SingularAttribute<AptAptmtContact, ?>[] attributes)
	   {
	      return repository.findBy(entity, start, max, attributes);
	   }

	   public Long countBy(AptAptmtContact entity, SingularAttribute<AptAptmtContact, ?>[] attributes)
	   {
	      return repository.count(entity, attributes);
	   }

	   public List<AptAptmtContact> findByLike(AptAptmtContact entity, int start, int max, SingularAttribute<AptAptmtContact, ?>[] attributes)
	   {
	      return repository.findByLike(entity, start, max, attributes);
	   }

	   public Long countByLike(AptAptmtContact entity, SingularAttribute<AptAptmtContact, ?>[] attributes)
	   {
	      return repository.countLike(entity, attributes);
	   }

	   private AptAptmtContact attach(AptAptmtContact entity)
	   {
	      if (entity == null)
	         return null;

	      return entity;
	   }

}
