package org.adorsys.adsales.api;

import java.util.ArrayList;
import java.util.List;

import org.adorsys.adsales.jpa.SlsInvcePtnr;
import org.adorsys.adsales.jpa.SlsInvoice;
import org.adorsys.adsales.jpa.SlsSalesOrder;

public class SlsInvoiceHolder {

	private SlsInvoice slsInvoice;
	
	private List<SlsInvceItemHolder> slsInvceItemsholder = new ArrayList<SlsInvceItemHolder>();
	
	private List<SlsInvcePtnrHolder> slsInvcePtnrsHolder = new ArrayList<SlsInvcePtnrHolder>();

	
	public List<SlsInvceItemHolder> getSlsInvceItemsholder() {
		return slsInvceItemsholder;
	}

	public void setSlsInvceItemsholder(List<SlsInvceItemHolder> slsInvceItemsholder) {
		this.slsInvceItemsholder = slsInvceItemsholder;
	}


	public SlsInvoice getSlsInvoice() {
		return slsInvoice;
	}

	public void setSlsInvoice(SlsInvoice slsInvoice) {
		this.slsInvoice = slsInvoice;
	}

	public List<SlsInvcePtnrHolder> getSlsInvcePtnrsHolder() {
		return slsInvcePtnrsHolder;
	}

	public void setSlsInvcePtnrsHolder(List<SlsInvcePtnrHolder> slsInvcePtnrsHolder) {
		this.slsInvcePtnrsHolder = slsInvcePtnrsHolder;
	}

	
}
