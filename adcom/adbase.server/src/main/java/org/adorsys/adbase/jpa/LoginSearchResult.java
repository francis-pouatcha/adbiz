package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Login> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private LoginSearchInput searchInput;

   public LoginSearchResult()
   {
      super();
   }

   public LoginSearchResult(Long count, List<Login> resultList,
         LoginSearchInput searchInput)
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

   public List<Login> getResultList()
   {
      return resultList;
   }

   public LoginSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Login> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(LoginSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
