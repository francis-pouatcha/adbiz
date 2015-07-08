package org.adorsys.adcshdwr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CdrPymtHstrySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CdrPymtHstry> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CdrPymtHstrySearchInput searchInput;

   public CdrPymtHstrySearchResult()
   {
      super();
   }

   public CdrPymtHstrySearchResult(Long count, List<CdrPymtHstry> resultList,
         CdrPymtHstrySearchInput searchInput)
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

   public List<CdrPymtHstry> getResultList()
   {
      return resultList;
   }

   public CdrPymtHstrySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CdrPymtHstry> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CdrPymtHstrySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
