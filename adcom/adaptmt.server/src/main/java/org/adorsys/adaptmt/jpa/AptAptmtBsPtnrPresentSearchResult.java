package org.adorsys.adaptmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AptAptmtBsPtnrPresentSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<AptAptmtBsPtnrPresent> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private AptAptmtBsPtnrPresentSearchInput searchInput;

   public AptAptmtBsPtnrPresentSearchResult()
   {
      super();
   }

   public AptAptmtBsPtnrPresentSearchResult(Long count, List<AptAptmtBsPtnrPresent> resultList,
		   AptAptmtBsPtnrPresentSearchInput searchInput)
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

   public List<AptAptmtBsPtnrPresent> getResultList()
   {
      return resultList;
   }

   public AptAptmtBsPtnrPresentSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<AptAptmtBsPtnrPresent> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(AptAptmtBsPtnrPresentSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
