package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserWorkspaceSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<UserWorkspace> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private UserWorkspaceSearchInput searchInput;

   public UserWorkspaceSearchResult()
   {
      super();
   }

   public UserWorkspaceSearchResult(Long count, List<UserWorkspace> resultList,
         UserWorkspaceSearchInput searchInput)
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

   public List<UserWorkspace> getResultList()
   {
      return resultList;
   }

   public UserWorkspaceSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<UserWorkspace> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(UserWorkspaceSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
