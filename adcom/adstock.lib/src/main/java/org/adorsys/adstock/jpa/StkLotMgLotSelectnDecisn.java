package org.adorsys.adstock.jpa;

/**
 * If we have lots and the user does not enter the lot
 * identification code and we have to achieve a lot selection, what do we do?
 * 
 * @author fpo
 *
 */
public enum StkLotMgLotSelectnDecisn {

	// Forced automatic. User is not prompted for a lot selection list. We use intelligence like lot selection scheme below, section, user location, stock quantity.
	AUTO,
	// We try to select automatically based on the section (storage, point of sales, aisle). And if at the end there is still some confusion, we prompt the user to decide.
	SEMIAUTO,
	// We always prompt the user with the list of found lots and the user must manually make a selection.
	MANUAL;
	
}
