package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StkMvntSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<StkMvnt> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private StkMvntSearchInput searchInput;

   public StkMvntSearchResult()
   {
      super();
   }

   public StkMvntSearchResult(Long count, List<StkMvnt> resultList,
         StkMvntSearchInput searchInput)
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

   public List<StkMvnt> getResultList()
   {
      return resultList;
   }

   public StkMvntSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<StkMvnt> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(StkMvntSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
