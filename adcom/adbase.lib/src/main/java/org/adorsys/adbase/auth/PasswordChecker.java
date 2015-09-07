package org.adorsys.adbase.auth;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class PasswordChecker {

	/** The salt. */
	public static final String SALT = "!+@_#)$(%*%&^" ;	

	/**
	 * Encode password.
	 * 
	 * @param password
	 *            the password
	 * @return the string
	 */
	public static String encodePassword(String password){
		return DigestUtils.md5Hex(password+SALT);
	}

	/**
	 * Check password.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * @return true, if successful
	 */
	public static boolean checkPassword(String source, String target){
		String encodedSource = encodePassword(source);
		return StringUtils.equals(encodedSource, target);
	}
}
