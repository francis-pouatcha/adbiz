/**
 * 
 */
package org.adorsys.adcore.utils;

/**
 * @author boriswaguia
 *
 */
public class Contract {
	
	public static void assertNotNull(Object object) {
		if(object == null) throw new NullPointerException();
	}
	
	public static void checkIllegalArgumentException(Object object) {
		if(object == null) throw new IllegalArgumentException();
	}
	
	public static void checkIllegalArgumentException(Object ... object) {
		for (Object obj : object) {
			if(obj == null) throw new IllegalArgumentException();
		}
	}
	
	public static void checkIllegalArgumentException(Object object,String message) {
		if(object == null) throw new IllegalArgumentException(message);
	}
	
	public static void checkIllegalStateException(Object object) {
		if(object == null) throw new IllegalStateException();
	}
	
	public static void checkIllegalStateException(Object object,String message) {
		if(object == null) throw new IllegalStateException(message);
	}
}
