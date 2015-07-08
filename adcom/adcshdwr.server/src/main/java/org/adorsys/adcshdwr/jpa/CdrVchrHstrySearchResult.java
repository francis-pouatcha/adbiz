package org.adorsys.adcshdwr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CdrVchrHstrySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CdrVchrHstry> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CdrVchrHstrySearchInput searchInput;

   public CdrVchrHstrySearchResult()
   {
      super();
   }

   public CdrVchrHstrySearchResult(Long count, List<CdrVchrHstry> resultList,
         CdrVchrHstrySearchInput searchInput)
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

   public List<CdrVchrHstry> getResultList()
   {
      return resultList;
   }

   public CdrVchrHstrySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CdrVchrHstry> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CdrVchrHstrySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
