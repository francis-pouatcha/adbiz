package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginNameSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<LoginName> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private LoginNameSearchInput searchInput;

   public LoginNameSearchResult()
   {
      super();
   }

   public LoginNameSearchResult(Long count, List<LoginName> resultList,
         LoginNameSearchInput searchInput)
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

   public List<LoginName> getResultList()
   {
      return resultList;
   }

   public LoginNameSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<LoginName> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(LoginNameSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }
}
