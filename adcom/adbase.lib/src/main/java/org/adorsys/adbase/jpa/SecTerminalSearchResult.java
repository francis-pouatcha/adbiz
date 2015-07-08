package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SecTerminalSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SecTerminal> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SecTerminalSearchInput searchInput;

   public SecTerminalSearchResult()
   {
      super();
   }

   public SecTerminalSearchResult(Long count, List<SecTerminal> resultList,
		   SecTerminalSearchInput searchInput)
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

   public List<SecTerminal> getResultList()
   {
      return resultList;
   }

   public SecTerminalSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SecTerminal> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SecTerminalSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
