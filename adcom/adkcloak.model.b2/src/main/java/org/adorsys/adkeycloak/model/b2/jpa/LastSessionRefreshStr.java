package org.adorsys.adkeycloak.model.b2.jpa;

public class LastSessionRefreshStr {

	public static final int toInt(String lastSessionRefreshStr){
		if(lastSessionRefreshStr==null) return 0;
		return Integer.parseInt(lastSessionRefreshStr);
	}
	
	public static final String toStr(int lastSessionRefresh){
		return ""+lastSessionRefresh;
	}
}
