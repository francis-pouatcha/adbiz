package org.adorsys.adcore.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CoreSearchResult<T>
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<T> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CoreSearchInput<T> searchInput;

   public CoreSearchResult()
   {
      super();
   }

   public CoreSearchResult(Long count, List<T> resultList,
         CoreSearchInput<T> searchInput)
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

   public List<T> getResultList()
   {
      return resultList;
   }

   public CoreSearchInput<T> getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<T> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CoreSearchInput<T> searchInput)
   {
      this.searchInput = searchInput;
   }

}
