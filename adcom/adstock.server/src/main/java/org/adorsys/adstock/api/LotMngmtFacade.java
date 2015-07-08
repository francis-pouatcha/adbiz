package org.adorsys.adstock.api;

import javax.ejb.Stateless;

import org.adorsys.adstock.vo.StkMvnts;

@Stateless
public class LotMngmtFacade {

	/**
	 * Checks if a selection applies to the corresponding stock management schemes defined.  
	 * 
	 * @param articleMvng
	 * @return
	 */
	public StkMvnts checkSelection(StkMvnts mvnts){
		return mvnts;
	}
	
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
	public StkMvnts selectLots(StkMvnts mvnts){
		return mvnts;
	}
	
}
