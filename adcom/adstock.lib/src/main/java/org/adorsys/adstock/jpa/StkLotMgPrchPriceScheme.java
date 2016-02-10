package org.adorsys.adstock.jpa;

/**
 * 
 * purchasePriceScheme : What purchase price are we going to be putting on this
 * lot. Mostly when the same lot gather to articles form many origins.
 * 
 * @author fpo
 *
 */
public enum StkLotMgPrchPriceScheme {
	// Promt the user to enter the purchase prise
	MANUAL, 
	// Take ove the purchase prise of the existing lot.
	KEEP_EXISTING,
	// If there is a new price, override te existing.
	OVERRIDE_EXISTING,
	// Take the average of both (existing + new) / 2
	SIMPLE_AVERAGE,
	// Take the average weighted by respective quantities
	// User delivered qty for enw stock
	WEIGHTED_AVERAGE_DLVRD,
	// Take the average weighted by respective quantities.
	// Use billed quantity for new stock.
	WEIGHTED_AVERAGE_BILLED,
	// Stock value divided by the stock quantity.
	STOCK_VALUE_BASED,
	// Formula
	FORMULA
}
