package org.adorsys.adcore.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class CoreAbstIdentifObjectSearchResult<E extends CoreAbstIdentifObject, I extends CoreAbstIdentifObjectSearchInput<E>>
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<E> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private I searchInput;
   
   public CoreAbstIdentifObjectSearchResult(){
   }

   public CoreAbstIdentifObjectSearchResult(Long count, List<E> resultList,
         I searchInput)
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

   public List<E> getResultList()
   {
      return resultList;
   }

   public I getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<E> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(I searchInput)
   {
      this.searchInput = searchInput;
   }
}
