package org.adorsys.adstock.api;

import org.adorsys.adstock.vo.StkMvnts;

/**
 * Verifies that an article is being taken from the correct lot.
 * 
 * @author francis
 *
 */
public interface StkLotEnforcer {
	
	/**
	 * Checks if a selection applies to the corresponding stock management schemes defined.  
	 * 
	 * @param articleMvng
	 * @return
	 */
	public StkMvnts checkSelection(StkMvnts mvnts);
}
