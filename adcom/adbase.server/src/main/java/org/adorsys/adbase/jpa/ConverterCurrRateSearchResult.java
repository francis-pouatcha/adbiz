package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConverterCurrRateSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<ConverterCurrRate> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private ConverterCurrRateSearchInput searchInput;

   public ConverterCurrRateSearchResult()
   {
      super();
   }

   public ConverterCurrRateSearchResult(Long count, List<ConverterCurrRate> resultList,
         ConverterCurrRateSearchInput searchInput)
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

   public List<ConverterCurrRate> getResultList()
   {
      return resultList;
   }

   public ConverterCurrRateSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<ConverterCurrRate> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(ConverterCurrRateSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
