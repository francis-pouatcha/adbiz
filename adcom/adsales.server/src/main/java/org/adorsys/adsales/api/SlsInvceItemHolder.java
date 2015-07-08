package org.adorsys.adsales.api;

import org.adorsys.adsales.jpa.SlsInvceItem;

public class SlsInvceItemHolder {

	private SlsInvceItem slsInvceItem;
	
	
	private boolean deleted;

	

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public SlsInvceItem getSlsInvceItem() {
		return slsInvceItem;
	}

	public void setSlsInvceItem(SlsInvceItem slsInvceItem) {
		this.slsInvceItem = slsInvceItem;
	}
}
