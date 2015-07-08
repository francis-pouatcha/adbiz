/**
 * 
 */
package org.adorsys.adcshdwr.api;

import org.adorsys.adcshdwr.jpa.CdrDsArtItem;

/**
 * @author boriswaguia
 *
 */
public class CdrDsArtItemHolder {
	
	private String artName;
	private String maxStockQty;
	private CdrDsArtItem item;
	private boolean deleted;
	public String getMaxStockQty() {
		return maxStockQty;
	}
	public void setMaxStockQty(String maxStockQty) {
		this.maxStockQty = maxStockQty;
	}
	public String getArtName() {
		return artName;
	}
	public void setArtName(String artName) {
		this.artName = artName;
	}
	public CdrDsArtItem getItem() {
		return item;
	}
	public void setItem(CdrDsArtItem item) {
		this.item = item;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	@Override
	public String toString() {
		return "CdrDsArtItemHolder [artName=" + artName + ", maxStockQty="
				+ maxStockQty + ", item=" + item + "]";
	}
}