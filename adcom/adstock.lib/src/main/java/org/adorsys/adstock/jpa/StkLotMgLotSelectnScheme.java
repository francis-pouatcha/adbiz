package org.adorsys.adstock.jpa;

/**
 * Criteria influencing auto selection.
 * - This can influence the order of choice shown to the user
 * - The forced selection
 * - The selection algorithm 
 * @author fpo
 *
 */
public enum StkLotMgLotSelectnScheme {
	// WE suspect the user always take good from same location.
	LRU_USER,
	// Section can physically limit location of good been sold.
	// If cashier is in a store location, probably good is from there.
	LRU_SECTION,
	// We suspect old sold first.
	FIFO,
	// Good in stack
	LIFO,
	// We promp the user.
	MANUAL,
	// LRU_USER, LRU_SECTION, FIFO, LIFO, MANUAL in that order.
	SMART,
}
