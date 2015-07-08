package org.adorsys.adcore.enums;

public enum CoreCurrencyEnum {
	
	XAF(0),EUR(2),NGN(0),USD(2);
	private final int dcmlPos;

	private CoreCurrencyEnum(int dcmlPos) {
		this.dcmlPos = dcmlPos;
	}

	public int getDcmlPos() {
		return dcmlPos;
	}

}
