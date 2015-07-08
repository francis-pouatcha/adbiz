package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OuWsRestrictionSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<OuWsRestriction> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private OuWsRestrictionSearchInput searchInput;

   public OuWsRestrictionSearchResult()
   {
      super();
   }

   public OuWsRestrictionSearchResult(Long count, List<OuWsRestriction> resultList,
         OuWsRestrictionSearchInput searchInput)
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

   public List<OuWsRestriction> getResultList()
   {
      return resultList;
   }

   public OuWsRestrictionSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<OuWsRestriction> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(OuWsRestrictionSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
