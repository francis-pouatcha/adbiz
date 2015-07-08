package org.adorsys.adcshdwr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CdrCstmrVchrSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CdrCstmrVchr> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CdrCstmrVchrSearchInput searchInput;

   public CdrCstmrVchrSearchResult()
   {
      super();
   }

   public CdrCstmrVchrSearchResult(Long count, List<CdrCstmrVchr> resultList,
         CdrCstmrVchrSearchInput searchInput)
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

   public List<CdrCstmrVchr> getResultList()
   {
      return resultList;
   }

   public CdrCstmrVchrSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CdrCstmrVchr> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CdrCstmrVchrSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
