package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RoleEntrySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<RoleEntry> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private RoleEntrySearchInput searchInput;

   public RoleEntrySearchResult()
   {
      super();
   }

   public RoleEntrySearchResult(Long count, List<RoleEntry> resultList,
         RoleEntrySearchInput searchInput)
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

   public List<RoleEntry> getResultList()
   {
      return resultList;
   }

   public RoleEntrySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<RoleEntry> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(RoleEntrySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
