package org.adorsys.adbase.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.SecTermSession;
import org.adorsys.adbase.repo.SecTermSessionRepository;

@Stateless
public class SecTermSessionEJB
{

   @Inject
   private SecTermSessionRepository repository;

   public SecTermSession create(SecTermSession entity)
   {
      return repository.save(attach(entity));
   }

   public SecTermSession deleteById(String id)
   {
      SecTermSession entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SecTermSession update(SecTermSession entity)
   {
      return repository.save(attach(entity));
   }

   public SecTermSession findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SecTermSession> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SecTermSession> findBy(SecTermSession entity, int start, int max, SingularAttribute<SecTermSession, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SecTermSession entity, SingularAttribute<SecTermSession, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SecTermSession> findByLike(SecTermSession entity, int start, int max, SingularAttribute<SecTermSession, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SecTermSession entity, SingularAttribute<SecTermSession, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SecTermSession attach(SecTermSession entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   
//	@PostConstruct
//	public void postConstruct(){
//		SecTermSession termSession = new SecTermSession();
//		termSession.setId("0031c74f-df0e-42ba-9e3b-e69f8a987dca");
//		termSession.setCreated(new Date());
//		termSession.setExpires(DateUtils.addYears(new Date(), 1));
//		termSession.setIpAddress("127.0.0.1");
//		termSession.setLangIso2("en");
//		termSession.setTermId("A01CMTest_20150328150924");
//		termSession.setTermName("A01CMTest");
//		termSession.setTermCredtl("67a7723c-83b1-446a-bf96-f536036dedb4");
//		repository.save(termSession);
//	}
}
