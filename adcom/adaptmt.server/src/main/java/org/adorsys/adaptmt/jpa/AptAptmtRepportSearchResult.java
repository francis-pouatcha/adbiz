package org.adorsys.adaptmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AptAptmtRepportSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<AptAptmtRepport> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private AptAptmtRepportSearchInput searchInput;

   public AptAptmtRepportSearchResult()
   {
      super();
   }

   public AptAptmtRepportSearchResult(Long count, List<AptAptmtRepport> resultList,
		   AptAptmtRepportSearchInput searchInput)
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

   public List<AptAptmtRepport> getResultList()
   {
      return resultList;
   }

   public AptAptmtRepportSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<AptAptmtRepport> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(AptAptmtRepportSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
