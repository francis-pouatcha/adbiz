package org.adorsys.adstock.jpa;

/**
 * 
 * salesPriceScheme : What sales price are we going to be putting on this lot.
 * Mostly when the same lot gather to articles form different origins or there is no lot management.
 * 
 * @author fpo
 *
 */
public enum StkLotMgSlsPriceScheme {
	// Promt the user to enter the sales price
	MANUAL,
	// Take ove the purchase price of the existing lot.
	KEEP_EXISTING,
	// If there is a new price, override the existing.
	OVERRIDE_EXISTING,
	// Take the average of both (existing + new) / 2
	SIMPLE_AVERAGE,
	// Take the average weighted by respective quantities
	// User delivered qty for new stock
	WEIGHTED_AVERAGE_DLVRD,
	// Take the average weighted by respective quantities.
	// Use billed quantity for new stock.
	WEIGHTED_AVERAGE_BILLED,
	// The computed purchase value plus sales margin.
	PURCH_VALUE_PLUS_SLS_MRGN,
	// Stock value divided by the stock quantity.
	STOCK_VALUE_PLUS_SLS_MRGN,
	// Formula
	FORMULA
}
