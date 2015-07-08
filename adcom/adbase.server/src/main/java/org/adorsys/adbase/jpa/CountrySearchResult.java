package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CountrySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Country> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CountrySearchInput searchInput;

   public CountrySearchResult()
   {
      super();
   }

   public CountrySearchResult(Long count, List<Country> resultList,
         CountrySearchInput searchInput)
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

   public List<Country> getResultList()
   {
      return resultList;
   }

   public CountrySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Country> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CountrySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
