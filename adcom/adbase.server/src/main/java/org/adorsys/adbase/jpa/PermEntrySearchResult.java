package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PermEntrySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<PermEntry> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private PermEntrySearchInput searchInput;

   public PermEntrySearchResult()
   {
      super();
   }

   public PermEntrySearchResult(Long count, List<PermEntry> resultList,
         PermEntrySearchInput searchInput)
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

   public List<PermEntry> getResultList()
   {
      return resultList;
   }

   public PermEntrySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<PermEntry> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(PermEntrySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
