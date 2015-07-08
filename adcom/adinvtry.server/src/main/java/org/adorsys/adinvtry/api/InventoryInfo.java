package org.adorsys.adinvtry.api;

import org.adorsys.adinvtry.jpa.InvInvtry;

public class InventoryInfo {
	public static String prinInfo(InvInvtry invInvtry){
		return invInvtry.getBsnsObjNbr() + "_" + invInvtry.getTxStatus() + "_" + invInvtry.getTxngUser();
	}
}
