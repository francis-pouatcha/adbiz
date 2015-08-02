package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginConfigurationSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<LoginConfiguration> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private LoginConfigurationSearchInput searchInput;

   public LoginConfigurationSearchResult()
   {
      super();
   }

   public LoginConfigurationSearchResult(Long count, List<LoginConfiguration> resultList,
		   LoginConfigurationSearchInput searchInput)
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

   public List<LoginConfiguration> getResultList()
   {
      return resultList;
   }

   public LoginConfigurationSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<LoginConfiguration> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(LoginConfigurationSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
