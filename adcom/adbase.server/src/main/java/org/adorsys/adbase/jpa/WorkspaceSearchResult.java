package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WorkspaceSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Workspace> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private WorkspaceSearchInput searchInput;

   public WorkspaceSearchResult()
   {
      super();
   }

   public WorkspaceSearchResult(Long count, List<Workspace> resultList,
         WorkspaceSearchInput searchInput)
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

   public List<Workspace> getResultList()
   {
      return resultList;
   }

   public WorkspaceSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Workspace> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(WorkspaceSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
