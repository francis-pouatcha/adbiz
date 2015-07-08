package org.adorsys.adcore.repo;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DataSourceProducer
{

   @PersistenceContext
   private EntityManager entityManager;

   @Produces
   public EntityManager create()
   {
      return entityManager;
   }
}
