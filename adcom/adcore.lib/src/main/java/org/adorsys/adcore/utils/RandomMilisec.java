package org.adorsys.adcore.utils;

import java.math.BigDecimal;

public class RandomMilisec {

	public static void doWait(){
		try {
			Thread.sleep(new BigDecimal(Math.random()*1000).longValue());
		} catch (InterruptedException e) {
			// Noop
		}
	}

	public static void doWait(int sec){
		try {
			Thread.sleep(new BigDecimal(Math.random()*1000*sec).longValue());
		} catch (InterruptedException e) {
			// Noop
		}
	}
}
