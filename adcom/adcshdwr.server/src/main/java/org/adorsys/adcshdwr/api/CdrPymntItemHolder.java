/**
 * 
 */
package org.adorsys.adcshdwr.api;

import org.adorsys.adcshdwr.jpa.CdrPymntItem;

/**
 * @author boriswaguia
 *
 */
public class CdrPymntItemHolder {
	private CdrPymntItem pymtItem;
	private boolean deleted;
	
	
	public CdrPymntItem getPymtItem() {
		return pymtItem;
	}
	public void setPymtItem(CdrPymntItem pymtItem) {
		this.pymtItem = pymtItem;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
