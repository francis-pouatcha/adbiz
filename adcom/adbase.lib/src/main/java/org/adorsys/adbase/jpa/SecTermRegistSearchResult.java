/**
 * 
 */
package org.adorsys.adbase.jpa;

import java.util.List;

/**
 * @author boriswaguia
 *
 */
public class SecTermRegistSearchResult {

	   /*
	    * The number of entities matching this search.
	    */
	   private Long count;

	   /*
	    * The result list.
	    */
	   private List<SecTermRegist> resultList;

	   /*
	    * The original search input object. For stateless clients.
	    */
	   private SecTermRegistSearchInput searchInput;

	   public SecTermRegistSearchResult()
	   {
	      super();
	   }

	   public SecTermRegistSearchResult(Long count, List<SecTermRegist> resultList,
			   SecTermRegistSearchInput searchInput)
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

	   public List<SecTermRegist> getResultList()
	   {
	      return resultList;
	   }

	   public SecTermRegistSearchInput getSearchInput()
	   {
	      return searchInput;
	   }

	   public void setCount(Long count)
	   {
	      this.count = count;
	   }

	   public void setResultList(List<SecTermRegist> resultList)
	   {
	      this.resultList = resultList;
	   }

	   public void setSearchInput(SecTermRegistSearchInput searchInput)
	   {
	      this.searchInput = searchInput;
	   }

}
