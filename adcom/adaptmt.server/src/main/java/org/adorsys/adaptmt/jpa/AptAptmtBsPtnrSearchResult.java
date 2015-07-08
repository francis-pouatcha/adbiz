package org.adorsys.adaptmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AptAptmtBsPtnrSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<AptAptmtBsPtnr> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private AptAptmtBsPtnrSearchInput searchInput;

   public AptAptmtBsPtnrSearchResult()
   {
      super();
   }

   public AptAptmtBsPtnrSearchResult(Long count, List<AptAptmtBsPtnr> resultList,
		   AptAptmtBsPtnrSearchInput searchInput)
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

   public List<AptAptmtBsPtnr> getResultList()
   {
      return resultList;
   }

   public AptAptmtBsPtnrSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<AptAptmtBsPtnr> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(AptAptmtBsPtnrSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
