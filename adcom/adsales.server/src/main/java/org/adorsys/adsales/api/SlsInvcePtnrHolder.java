package org.adorsys.adsales.api;

import org.adorsys.adsales.jpa.SlsInvcePtnr;

public class SlsInvcePtnrHolder {

	private SlsInvcePtnr slsInvcePtnr;
	
	private boolean deleted;

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public SlsInvcePtnr getSlsInvcePtnr() {
		return slsInvcePtnr;
	}

	public void setSlsInvcePtnr(SlsInvcePtnr slsInvcePtnr) {
		this.slsInvcePtnr = slsInvcePtnr;
	}
	
}
