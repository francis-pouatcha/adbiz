package org.adorsys.adbase.auth;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.adorsys.adcore.auth.AuthParams;
import org.apache.commons.lang3.StringUtils;

public class BasicAuthUtils {

    public static final String BASIC_PREFIX = "Basic ";

    public static AuthParams readAuthHeader(HttpServletRequest request){
    	String chunks = parse(request);
    	return AuthParams.fromString(chunks);
    }

    private static String parse(HttpServletRequest request){
    	String authorizationHeaderBase64 = request.getHeader("authorization");
    	if(StringUtils.isBlank(authorizationHeaderBase64)) return null;
    	if(!StringUtils.startsWithIgnoreCase(authorizationHeaderBase64, BASIC_PREFIX)) return null;
    	authorizationHeaderBase64 = StringUtils.substringAfter(authorizationHeaderBase64.trim(), " ");
    	byte[] decodeBase64 = org.apache.commons.codec.binary.Base64.decodeBase64(authorizationHeaderBase64);
    	String authorizationHeaderStr;
		try {
			authorizationHeaderStr = StringUtils.toString(decodeBase64, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
    	if(StringUtils.isBlank(authorizationHeaderStr)) return null;
    	return authorizationHeaderStr;
    }
}
