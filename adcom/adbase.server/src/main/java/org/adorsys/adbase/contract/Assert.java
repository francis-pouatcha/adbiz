/**
 * 
 */
package org.adorsys.adbase.contract;

/**
 * @author boriswaguia
 *
 */
public class Assert {
	
	public static void notNull(Object object){
		if(object == null){
			throw new NullPointerException();
		}
	}

	public static void notNull(Object object,String msg){
		msg= msg==null?"":msg;
		if(object == null){
			throw new NullPointerException(msg);
		}
	}
}
