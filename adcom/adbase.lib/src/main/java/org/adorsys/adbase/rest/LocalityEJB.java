package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.Locality;
import org.adorsys.adbase.jpa.LocalitySearchInput;
import org.adorsys.adbase.jpa.LocalitySearchResult;
import org.adorsys.adbase.repo.LocalityRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class LocalityEJB
{
	
	@Inject
	private EntityManager em ;

   @Inject
   private LocalityRepository repository;

   public Locality create(Locality entity)
   {
      return repository.save(attach(entity));
   }

   public Locality deleteById(String id)
   {
      Locality entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }


   public Locality deleteCustomById(String id)
   {
      Locality entity = repository.findBy(id);
      if (entity != null)
      {
    	  entity.setValidTo(new Date());
    	  repository.save(entity);
      }
      return entity;
   }

   public Locality update(Locality entity)
   {
      return repository.save(attach(entity));
   }

   public Locality findById(String id)
   {
      return repository.findBy(id);
   }

   public List<Locality> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Locality> findBy(Locality entity, int start, int max, SingularAttribute<Locality, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Locality entity, SingularAttribute<Locality, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Locality> findByLike(Locality entity, int start, int max, SingularAttribute<Locality, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Locality entity, SingularAttribute<Locality, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Locality attach(Locality entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public Locality findByIdentif(String identif, Date validOn){
	   List<Locality> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
   public LocalitySearchResult  findAllActiveLocality(LocalitySearchInput searchInput){
	   Date date = new Date();
	   	   Long count = repository.countActiveLocality(date);
	   List<Locality> resultList = repository.findActiveLocality(date).firstResult(searchInput.getStart()).maxResults(searchInput.getMax()).getResultList();
	   LocalitySearchResult searchResult = new LocalitySearchResult();
	   searchResult.setCount(count);
	   searchResult.setResultList(resultList);
	   searchResult.setSearchInput(searchInput);
	   return searchResult ;
	   
   }
   
   public LocalitySearchResult doFind(LocalitySearchInput searchInput){	   

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT l FROM Locality AS l WHERE l.validFrom <=:dateDay AND (l.validTo >:dateDay OR l.validTo IS NULL) ");
		
		
		if(StringUtils.isNotBlank(searchInput.getEntity().getOuIdentif())){

			sb.append("AND LOWER(l.ouIdentif) LIKE LOWER(:ouIdentif) ");
		}

		if(StringUtils.isNotBlank(searchInput.getEntity().getCtryISO3())){

			sb.append("AND LOWER(l.ctryISO3) LIKE LOWER(:ctryISO3) ");
		}

		if(StringUtils.isNotBlank(searchInput.getEntity().getRegion())){

			sb.append("AND LOWER(l.region) LIKE LOWER(:region) ");
		}
		
		if(StringUtils.isNotBlank(searchInput.getEntity().getLocStr())){

			sb.append("AND LOWER(l.locStr) LIKE LOWER(:locStr) ");
		}

		String query = sb.toString();
		
		Query createQuery = em.createQuery(query);
		createQuery.setParameter("dateDay", new Date());

		if(StringUtils.isNotBlank(searchInput.getEntity().getOuIdentif())){

			String ouIdentif = searchInput.getEntity().getOuIdentif()+"%";
			createQuery.setParameter("ouIdentif", ouIdentif);
		}

		if(StringUtils.isNotBlank(searchInput.getEntity().getCtryISO3())){

			String ctryISO3 = searchInput.getEntity().getCtryISO3()+"%";
			createQuery.setParameter("ctryISO3", ctryISO3);
		}
		
		if(StringUtils.isNotBlank(searchInput.getEntity().getRegion())){

			String region = searchInput.getEntity().getRegion()+"%";
			createQuery.setParameter("region", region);
		}

		if(StringUtils.isNotBlank(searchInput.getEntity().getLocStr())){

			String locStr = searchInput.getEntity().getLocStr()+"%";
			createQuery.setParameter("locStr", locStr);
		}

		@SuppressWarnings("unchecked")
		List<Locality> resultList = createQuery.getResultList();
		
		if(searchInput.getStart() >= 0){
			createQuery.setFirstResult(searchInput.getStart());
		}
		if(searchInput.getMax() > 0){
			createQuery.setMaxResults(searchInput.getMax());
		}
		List resultList2 = createQuery.getResultList();

		LocalitySearchResult localitySearchResult = new LocalitySearchResult();
		localitySearchResult.setCount(Long.valueOf(resultList.size()));
		localitySearchResult.setSearchInput(searchInput);
		localitySearchResult.setResultList(resultList2);

		return  localitySearchResult;	   
	}
}
