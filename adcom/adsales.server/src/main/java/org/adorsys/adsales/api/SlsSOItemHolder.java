package org.adorsys.adsales.api;

import org.adorsys.adsales.jpa.SlsSOItem;

public class SlsSOItemHolder {

	private SlsSOItem slsSOItem;
	
	private boolean deleted;

	public SlsSOItem getSlsSOItem() {
		return slsSOItem;
	}

	public void setSlsSOItem(SlsSOItem slsSOItem) {
		this.slsSOItem = slsSOItem;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
