package org.adorsys.adcore.jpa;

public class CoreBsnsObjInfo {
	public static String prinInfo(CoreAbstBsnsObject obj){
		return obj.getIdentif() + "_ou_"+ obj.getOuIdentif() +"_p_" + obj.getPrchGrossPrcPreTax() + "_s_" + obj.getSlsGrossPrcPreTax();
	}
}
