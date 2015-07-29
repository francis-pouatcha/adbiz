package org.adorsys.adaptmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AptAptmtContactSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<AptAptmtContact> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private AptAptmtContactSearchInput searchInput;

   public AptAptmtContactSearchResult()
   {
      super();
   }

   public AptAptmtContactSearchResult(Long count, List<AptAptmtContact> resultList,
		   AptAptmtContactSearchInput searchInput)
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

   public List<AptAptmtContact> getResultList()
   {
      return resultList;
   }

   public AptAptmtContactSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<AptAptmtContact> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(AptAptmtContactSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
