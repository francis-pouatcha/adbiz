package org.adorsys.adcost.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CstBearerSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CstBearer> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CstBearerSearchInput searchInput;

   public CstBearerSearchResult()
   {
      super();
   }

   public CstBearerSearchResult(Long count, List<CstBearer> resultList,
         CstBearerSearchInput searchInput)
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

   public List<CstBearer> getResultList()
   {
      return resultList;
   }

   public CstBearerSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CstBearer> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CstBearerSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
