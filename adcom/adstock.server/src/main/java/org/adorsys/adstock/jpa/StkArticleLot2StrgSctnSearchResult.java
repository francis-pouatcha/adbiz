package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StkArticleLot2StrgSctnSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<StkArticleLot2StrgSctn> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private StkArticleLot2StrgSctnSearchInput searchInput;

   public StkArticleLot2StrgSctnSearchResult()
   {
      super();
   }

   public StkArticleLot2StrgSctnSearchResult(Long count, List<StkArticleLot2StrgSctn> resultList,
		   StkArticleLot2StrgSctnSearchInput searchInput)
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

   public List<StkArticleLot2StrgSctn> getResultList()
   {
      return resultList;
   }

   public StkArticleLot2StrgSctnSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<StkArticleLot2StrgSctn> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(StkArticleLot2StrgSctnSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
