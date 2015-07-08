package org.adorsys.adterm.filter;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adorsys.adcore.auth.SecureTermCookieUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * The Class InitFilter.
 */
@WebFilter(urlPatterns = "/*")
public class RegistrationFilter implements Filter {

	@Inject
	private RegistrationEJB registrationEJB;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		String authorizationHeader = request.getHeader("authorization");
		if (authorizationHeader == null) {
			requestAuthorization(request, response);
			return;
		}
		
		MessageFormat basicAuthFormat = new MessageFormat("Basic {0}");
		MessageFormat userPasswordFormat = new MessageFormat("{0}:{1}");
		String registKey = null;
		String registPwd = null;
		try {
			String userPasswordBase64 = (String) basicAuthFormat.parse(authorizationHeader)[0];
			String userPassword = new String(javax.xml.bind.DatatypeConverter.parseBase64Binary(userPasswordBase64), "UTF-8");
			Object[] userPasswordSplitted = userPasswordFormat.parse(userPassword);
			
			registKey = (String) userPasswordSplitted[0];
			registPwd = (String) userPasswordSplitted[1];

		} catch (ParseException e) {
			requestAuthorization(request, response);
		}
		
		if(StringUtils.isBlank(registKey) || StringUtils.isBlank(registPwd)){
			requestAuthorization(request, response);			
			return;			
		}
		String remoteAddr = request.getRemoteAddr();

		String cookieStr = registrationEJB.register(registKey, registPwd, remoteAddr);
		if(StringUtils.isBlank(cookieStr)){
			requestAuthorization(request, response);			
			return;			
		}

		Cookie cookie = new SecureTermCookieUtils().setSecureCookie(cookieStr, request.getServerName(), 60*60*24*365);
		response.addCookie(cookie);
		response.sendRedirect("/adlogin.client");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

	private void requestAuthorization(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (request.getHeader("X-No-WWW-Authenticate") == null) {
			response.setHeader("WWW-Authenticate", "Basic realm=\"adterm\"");
		}
		response.sendError(401, "please authorize");
	}
}
