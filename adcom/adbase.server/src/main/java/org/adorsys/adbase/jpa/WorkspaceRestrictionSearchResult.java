package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WorkspaceRestrictionSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<WorkspaceRestriction> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private WorkspaceRestrictionSearchInput searchInput;

   public WorkspaceRestrictionSearchResult()
   {
      super();
   }

   public WorkspaceRestrictionSearchResult(Long count, List<WorkspaceRestriction> resultList,
         WorkspaceRestrictionSearchInput searchInput)
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

   public List<WorkspaceRestriction> getResultList()
   {
      return resultList;
   }

   public WorkspaceRestrictionSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<WorkspaceRestriction> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(WorkspaceRestrictionSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
