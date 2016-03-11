package org.adorsys.adkcloak.adapter.file;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.adorsys.adcore.env.EnvProperties;
import org.apache.commons.lang3.StringUtils;

@WebFilter("/*")
public class UriInfoFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String envOrSystProp = EnvProperties.getEnvOrSystProp("AUTH_SERVER_URL");
		if (StringUtils.isBlank(envOrSystProp)){
			HttpServletRequest req = (HttpServletRequest) request;
			String serverUriString = req.getRequestURL().toString();
			if(!StringUtils.contains(serverUriString, "://")) throw new IllegalStateException("Not an absolute url : " + serverUriString);
			String hostAndPort = StringUtils.substringBetween(serverUriString, "://", "/");
			String scheme = StringUtils.substringBefore(serverUriString, hostAndPort);
			String mainUrl = scheme + hostAndPort;
			System.setProperty("AUTH_SERVER_URL", mainUrl + "/auth");
		}
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
