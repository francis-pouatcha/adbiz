package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LocalitySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Locality> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private LocalitySearchInput searchInput;

   public LocalitySearchResult()
   {
      super();
   }

   public LocalitySearchResult(Long count, List<Locality> resultList,
         LocalitySearchInput searchInput)
   {
      super();
      this.count = count;
      this.resultList = resultList;
      this.searchInput = searchInput;
   }

   public Long getCount()
   {
      return count;
   }

   public List<Locality> getResultList()
   {
      return resultList;
   }

   public LocalitySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Locality> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(LocalitySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
