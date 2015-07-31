package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BaseCountryNameSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<BaseCountryName> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private BaseCountryNameSearchInput searchInput;

   public BaseCountryNameSearchResult()
   {
      super();
   }

   public BaseCountryNameSearchResult(Long count, List<BaseCountryName> resultList,
         BaseCountryNameSearchInput searchInput)
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

   public List<BaseCountryName> getResultList()
   {
      return resultList;
   }

   public BaseCountryNameSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<BaseCountryName> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(BaseCountryNameSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
