package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PricingCurrRateSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<PricingCurrRate> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private PricingCurrRateSearchInput searchInput;

   public PricingCurrRateSearchResult()
   {
      super();
   }

   public PricingCurrRateSearchResult(Long count, List<PricingCurrRate> resultList,
         PricingCurrRateSearchInput searchInput)
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

   public List<PricingCurrRate> getResultList()
   {
      return resultList;
   }

   public PricingCurrRateSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<PricingCurrRate> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(PricingCurrRateSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
