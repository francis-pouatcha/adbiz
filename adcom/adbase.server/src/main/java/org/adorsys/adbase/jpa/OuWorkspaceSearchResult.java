package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OuWorkspaceSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<OuWorkspace> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private OuWorkspaceSearchInput searchInput;

   public OuWorkspaceSearchResult()
   {
      super();
   }

   public OuWorkspaceSearchResult(Long count, List<OuWorkspace> resultList,
         OuWorkspaceSearchInput searchInput)
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

   public List<OuWorkspace> getResultList()
   {
      return resultList;
   }

   public OuWorkspaceSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<OuWorkspace> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(OuWorkspaceSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
