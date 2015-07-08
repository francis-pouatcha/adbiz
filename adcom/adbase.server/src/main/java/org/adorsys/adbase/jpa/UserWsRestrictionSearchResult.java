package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserWsRestrictionSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<UserWsRestriction> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private UserWsRestrictionSearchInput searchInput;

   public UserWsRestrictionSearchResult()
   {
      super();
   }

   public UserWsRestrictionSearchResult(Long count, List<UserWsRestriction> resultList,
         UserWsRestrictionSearchInput searchInput)
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

   public List<UserWsRestriction> getResultList()
   {
      return resultList;
   }

   public UserWsRestrictionSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<UserWsRestriction> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(UserWsRestrictionSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
