package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OuTypeSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<OuType> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private OuTypeSearchInput searchInput;

   public OuTypeSearchResult()
   {
      super();
   }

   public OuTypeSearchResult(Long count, List<OuType> resultList,
         OuTypeSearchInput searchInput)
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

   public List<OuType> getResultList()
   {
      return resultList;
   }

   public OuTypeSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<OuType> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(OuTypeSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
