package org.adorsys.adaptmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AptAptmtSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<AptAptmt> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private AptAptmtSearchInput searchInput;

   public AptAptmtSearchResult()
   {
      super();
   }

   public AptAptmtSearchResult(Long count, List<AptAptmt> resultList,
         AptAptmtSearchInput searchInput)
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

   public List<AptAptmt> getResultList()
   {
      return resultList;
   }

   public AptAptmtSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<AptAptmt> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(AptAptmtSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
