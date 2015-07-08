package org.adorsys.adstock.api;

import org.adorsys.adstock.vo.StkMvnts;

/**
 * Helps with the selection of the lot to be used when taking an article out of the stock.
 * 
 * @author francis
 *
 */
public interface StkLotSelector {
	
	/**
	 * Takes a list of articles pic and requested quantities and decides which lots are involved in the 
	 * movement.
	 * 
	 * The input mvnt must only specify the article cip and the rquested quantities.
	 * 
	 * @param mvnts : the article cip and quantities.
	 * 
	 * @return
	 */
	public StkMvnts selectLots(StkMvnts mvnts);
}
