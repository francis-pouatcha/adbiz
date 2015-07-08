package org.adorsys.adsales.api;

import org.adorsys.adsales.jpa.SlsSOPtnr;

public class SlsSOPtnrHolder {

	private SlsSOPtnr slsSOPtnr;
	
	private boolean deleted;

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public SlsSOPtnr getSlsSOPtnr() {
		return slsSOPtnr;
	}

	public void setSlsSOPtnr(SlsSOPtnr slsSOPtnr) {
		this.slsSOPtnr = slsSOPtnr;
	}

	
}
