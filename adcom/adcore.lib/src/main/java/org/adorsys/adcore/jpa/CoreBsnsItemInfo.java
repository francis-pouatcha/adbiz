package org.adorsys.adcore.jpa;

public class CoreBsnsItemInfo {
	public static String prinInfo(CoreAbstBsnsItem item){
		return item.getIdentif() + "_ctnr_"+ item.getCntnrIdentif() +"_q_" + item.getTrgtQty() + "_p_" + item.getPrchGrossPrcPreTax() +"_s_" + item.getSlsGrossPrcPreTax();
	}
}
