package adcom.test.utils;


import java.math.BigDecimal;
import java.sql.Time;

import com.google.common.base.Ticker;

public class RandomMilisec {
	public static int time  = 1000 ;
	public static void doWait(Long value){
		
		try {
			if(value!=null)
				time = value.intValue(); 
			
			Thread.sleep(new BigDecimal(Math.random()*time).longValue());
		} catch (InterruptedException e) {
			// Noop
		}
	}
}
